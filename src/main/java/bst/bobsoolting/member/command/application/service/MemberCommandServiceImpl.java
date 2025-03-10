package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.config.KakaoProperties;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.query.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

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
    public MemberDTO createMember(MemberDTO newMemberDTO) {
        try {
            String memberId = generateMemberId();
            log.info("생성된 memberId: {}", memberId);

            Member newMember = memberConverter.fromDTOToEntity(newMemberDTO, memberId);

            memberRepository.save(newMember);
            log.info("회원가입 완료: kakaoId={}, memberId={}", newMember.getKakaoId(), newMember.getMemberId());
            return memberConverter.fromEntityToDTO(newMember);
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage(), e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public MemberDTO updateMemberProfile(OAuth2User user, RequestUpdateProfileVO updateInfo) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        log.info("프로필 수정 진행. kakaoId={}, 변경 정보={}", kakaoId, updateInfo);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

        boolean isUpdated = false;
        if (updateInfo.getProfileImage() != null) {
            member.setProfileImage(updateInfo.getProfileImage());
            isUpdated = true;
        }
        if (updateInfo.getNickname() != null) {
            member.setNickname(updateInfo.getNickname());
            isUpdated = true;
        }
        if (updateInfo.getDepartment() != null) {
            member.setDepartment(updateInfo.getDepartment());
            isUpdated = true;
        }

        if (!isUpdated) {
            log.info("변경할 항목이 없음. kakaoId={}", kakaoId);
            return memberConverter.fromEntityToDTO(member);
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
    @Transactional
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

    private String generateMemberId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(20);
        return datePart + uuidPart;
    }
}
