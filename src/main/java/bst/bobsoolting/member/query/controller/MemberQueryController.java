package bst.bobsoolting.member.query.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import bst.bobsoolting.member.query.controller.docs.MemberQueryControllerDocs;
import bst.bobsoolting.member.query.repository.MemberMapper;
import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberQueryController implements MemberQueryControllerDocs {

    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;
    private final MemberMapper memberMapper;

    @GetMapping("/loginSuccess")
    public ResponseEntity<Map<String, String>> loginSuccess(HttpServletRequest request) {
        String kakaoId = SecurityUtil.getKakaoId();
        if (kakaoId == null) {
            log.error("❌ OAuth2User 정보가 없습니다. 인증 실패 가능성 있음.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "OAuth2 인증 정보가 없습니다."));
        }
        log.info("✅ 카카오 로그인 성공: {}", kakaoId);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        boolean isNewMember = (existingMember == null);
        String accessToken = extractAccessTokenFromSecurityContext();

        Map<String, String> response = new HashMap<>();
        response.put("message", isNewMember ? "신규 회원 - 추가 정보 입력 필요" : "기존 회원 로그인 성공");
        response.put("access_token", accessToken);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/loginFailure")
    public ResponseEntity<String> loginFailure() {
        log.info("카카오 로그인 실패");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    @GetMapping("/my-page")
    public ResponseEntity<ResponseProfileVO> getMyProfile() {
        String kakaoId = SecurityUtil.getKakaoId();
        log.info("마이페이지 조회 요청: {}", kakaoId);
        try {
            MemberDTO memberDTO = memberQueryService.getMemberProfile(kakaoId);
            ResponseProfileVO profileVO = memberConverter.fromDTOToProfileVO(memberDTO);
            return ResponseEntity.ok(profileVO);
        } catch (CommonException e) {
            log.error("마이페이지 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDetailVO> getMyProfileDetails() {
        String kakaoId = SecurityUtil.getKakaoId();
        log.info("프로필 상세 조회 요청: {}", kakaoId);
        try {
            MemberDTO memberDTO = memberQueryService.getMemberDetail(kakaoId);
            ResponseDetailVO detailVO = memberConverter.fromDTOToDetailVO(memberDTO);
            return ResponseEntity.ok(detailVO);
        } catch (CommonException e) {
            log.error("프로필 상세 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String extractAccessTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String token) {
            return token;
        }
        return null;
    }
}
