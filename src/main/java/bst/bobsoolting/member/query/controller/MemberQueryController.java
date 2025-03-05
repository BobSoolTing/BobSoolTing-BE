package bst.bobsoolting.member.query.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import bst.bobsoolting.member.query.controller.docs.MemberQueryControllerDocs;
import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberQueryController implements MemberQueryControllerDocs {

    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;

    @GetMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        log.info("카카오 로그인 성공: {}", kakaoId);
        try {
            memberQueryService.processLoginSuccess(kakaoId);
            return ResponseEntity.ok("로그인 성공: " + kakaoId);
        } catch (CommonException e) {
            log.error("로그인 성공 처리 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 성공 처리 중 오류가 발생했습니다");
        }
    }

    @GetMapping("/loginFailure")
    public ResponseEntity<String> loginFailure() {
        log.info("카카오 로그인 실패");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    @GetMapping("/my-page")
    public ResponseEntity<ResponseProfileVO> getMyProfile(@AuthenticationPrincipal OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
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
    public ResponseEntity<ResponseDetailVO> getMyProfileDetails(@AuthenticationPrincipal OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
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
}
