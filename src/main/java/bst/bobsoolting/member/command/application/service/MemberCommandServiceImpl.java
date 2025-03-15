package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.config.KakaoProperties;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.query.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;
    private final KakaoProperties kakaoProperties;
    private final StringRedisTemplate redisTemplate;

    @Override
    @Transactional
    public MemberDTO updateMemberAdditionalInfo(String kakaoId, RequestAdditionalRegisterVO info) {
        log.info("추가 회원가입 정보 업데이트 진행. kakaoId={}, 추가 정보={}", kakaoId, info);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        if (existingMember == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

        validateInput(info);

        existingMember.setUniversity(info.getUniversity());
        existingMember.setDepartment(info.getDepartment());
        existingMember.setStudentNumber(info.getStudentNumber());
        existingMember.setPhone(info.getPhone());
        existingMember.setGender(info.getGender());
        existingMember.setBirth(info.getBirth());

        memberRepository.save(existingMember);
        log.info("추가 정보 업데이트 완료. kakaoId={}", kakaoId);

        return memberConverter.fromEntityToDTO(existingMember);
    }

    @Override
    public void storeRefreshToken(String kakaoId, String refreshToken) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String key = "refresh_token:" + kakaoId;
        ops.set(key, refreshToken, 7, TimeUnit.DAYS);

        log.info("✅ Refresh Token 저장 완료 - Kakao ID: {}, Token: {}", kakaoId, refreshToken);
    }

    @Override
    @Transactional
    public MemberDTO updateMemberProfile(String kakaoId, RequestUpdateProfileVO updateInfo) {
        log.info("프로필 수정 진행. kakaoId={}, 변경 정보={}", kakaoId, updateInfo);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

        if (updateInfo.getProfileImage() != null) {
            member.setProfileImage(updateInfo.getProfileImage());
        }
        if (updateInfo.getNickname() != null) {
            member.setNickname(updateInfo.getNickname());
        }
        if (updateInfo.getDepartment() != null) {
            member.setDepartment(updateInfo.getDepartment());
        }

        memberRepository.save(member);
        log.info("프로필 수정 완료. kakaoId={}, 수정된 정보={}", kakaoId, updateInfo);

        return memberConverter.fromEntityToDTO(member);
    }

    @Override
    @Transactional
    public String getKakaoAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.getClientId());
        body.add("redirect_uri", kakaoProperties.getRedirectUri());
        body.add("code", code);
        body.add("client_secret", kakaoProperties.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        String accessToken = (String) response.getBody().get("access_token");
        log.info("✅ 카카오에서 받은 Access Token: {}", accessToken);  // <--- 로그 추가!

        if (accessToken == null) {
            throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);
        }

        return accessToken;
    }

    @Override
    @Transactional
    public MemberDTO getKakaoUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);

        Map<String, Object> responseBody = response.getBody();
        log.info("✅ 카카오 사용자 정보: {}", responseBody);

        String kakaoId = String.valueOf(responseBody.get("id"));

        if (kakaoId == null) throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);

        return createOrUpdateMember(kakaoId);
    }

    @Override
    public MemberDTO createOrUpdateMember(String kakaoId) {
        Member existingMember = memberMapper.findByKakaoId(kakaoId);

        if (existingMember == null) {
            String memberId = generateMemberId();
            Member newMember = Member.builder()
                    .memberId(memberId)
                    .kakaoId(kakaoId)
                    .nickname(null)
                    .profileImage(null)
                    .gender(MemberGender.DEFAULT)
                    .birth(null)
                    .university(null)
                    .department(null)
                    .studentNumber(null)
                    .rating(0.0f)
                    .memberRole(MemberRole.ROLE_USER)
                    .build();

            memberRepository.save(newMember);
            log.info("신규 회원 생성: kakaoId={}, memberId={}", kakaoId, memberId);
            return memberConverter.fromEntityToDTO(newMember);
        } else {
            memberRepository.save(existingMember);
            log.info("기존 회원 정보 업데이트: kakaoId={}, memberId={}", kakaoId, existingMember.getMemberId());
            return memberConverter.fromEntityToDTO(existingMember);
        }
    }

    @Override
    public String getRefreshToken(String kakaoId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get("refresh_token:" + kakaoId);
    }

    private String generateMemberId() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + UUID.randomUUID().toString().replace("-", "").substring(20);
    }

    private void validateInput(RequestAdditionalRegisterVO info) {
        if (!Pattern.matches("^[a-zA-Z0-9가-힣]{2,8}$", info.getNickname())) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!Pattern.matches("^[a-zA-Z0-9가-힣]{4,16}$", info.getUniversity())) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!Pattern.matches("^[a-zA-Z0-9가-힣]{4,16}$", info.getDepartment())) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!Pattern.matches("^\\d{6,10}$", String.valueOf(info.getStudentNumber()))) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!Pattern.matches("^(010-\\d{4}-\\d{4})$", info.getPhone())) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!info.getGender().name().matches("MALE|FEMALE")) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}