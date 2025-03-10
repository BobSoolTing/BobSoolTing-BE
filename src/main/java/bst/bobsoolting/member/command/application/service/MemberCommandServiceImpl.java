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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;
    private final KakaoProperties kakaoProperties;

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

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> responseBody = response.getBody();
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");

        String kakaoId = String.valueOf(responseBody.get("id"));
        String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");

        return createOrUpdateMember(kakaoId, nickname);
    }

    @Override
    public MemberDTO createOrUpdateMember(String kakaoId, String nickname) {
        Member existingMember = memberMapper.findByKakaoId(kakaoId);

        if (existingMember == null) {
            String memberId = generateMemberId();
            Member newMember = Member.builder()
                    .memberId(memberId)
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .profileImage(null)
                    .gender(null)
                    .birth(null)
                    .university(null)
                    .department(null)
                    .studentNumber(0)
                    .rating(0.0f)
                    .memberRole(null)
                    .build();

            memberRepository.save(newMember);
            log.info("신규 회원 생성: kakaoId={}, memberId={}", kakaoId, memberId);
            return memberConverter.fromEntityToDTO(newMember);
        } else {
            existingMember.setNickname(nickname);

            memberRepository.save(existingMember);
            log.info("기존 회원 정보 업데이트: kakaoId={}, memberId={}", kakaoId, existingMember.getMemberId());
            return memberConverter.fromEntityToDTO(existingMember);
        }
    }

    private Member createMember(String kakaoId) {
        return Member.builder()
                .memberId(generateMemberId())
                .kakaoId(kakaoId)
                .nickname("신규 유저")
                .phone("")
                .profileImage("")
                .gender(MemberGender.DEFAULT)
                .birth(new Date())
                .university("미입력")
                .department("미입력")
                .studentNumber(0)
                .rating(0.0f)
                .memberRole(MemberRole.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
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
        if (!info.getGender().name().matches("MAN|WOMAN")) {
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}