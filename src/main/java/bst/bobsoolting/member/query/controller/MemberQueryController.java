package bst.bobsoolting.member.query.controller;

import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.common.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryService memberQueryService;

    @Operation(description = "카카오 OAuth2 로그인 성공 후 처리")
    @GetMapping("/loginSuccess")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User user) {
        log.info("카카오 로그인 성공: {}", user);
        try {
            memberQueryService.processLoginSuccess(user);
            return ResponseEntity.ok("로그인 성공: " + user.getAttribute("id"));
        } catch (CommonException e) {
            log.error("로그인 성공 처리 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 성공 처리 중 오류가 발생했습니다");
        }
    }

    @Operation(description = "카카오 OAuth2 로그인 실패 처리")
    @GetMapping("/loginFailure")
    public ResponseEntity<?> loginFailure() {
        log.info("카카오 로그인 실패");
        try {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        } catch (Exception e) {
            log.error("로그인 실패 처리 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패 처리 중 오류가 발생했습니다");
        }
    }
}
