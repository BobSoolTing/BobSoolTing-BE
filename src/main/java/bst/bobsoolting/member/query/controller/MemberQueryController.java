package bst.bobsoolting.member.query.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
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

import java.util.Optional;

@RestController
@RequestMapping("api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;

    @Operation(summary = "카카오 OAuth2 로그인 성공 후 처리")
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

    @Operation(summary = "카카오 OAuth2 로그인 실패 처리")
    @GetMapping("/loginFailure")
    public ResponseEntity<?> loginFailure() {
        log.info("카카오 로그인 실패");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    @Operation(description = "마이페이지 조회")
    @GetMapping("/my-page")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal OAuth2User user) {
        log.info("마이페이지 조회 요청: {}", Optional.ofNullable(user.getAttribute("id")));
        try {
            MemberDTO memberDTO = memberQueryService.getMemberProfile(user);
            ResponseProfileVO profileVO = memberConverter.fromDTOToProfileVO(memberDTO);
            return ResponseEntity.ok(profileVO);
        } catch (CommonException e) {
            log.error("마이페이지 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("마이페이지 조회 중 오류 발생");
        }
    }

    @Operation(description = "프로필 상세 조회")
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfileDetails(@AuthenticationPrincipal OAuth2User user) {
        log.info("프로필 상세 조회 요청: {}", Optional.ofNullable(user.getAttribute("id")));
        try {
            MemberDTO memberDTO = memberQueryService.getMemberDetail(user);
            ResponseDetailVO detailVO = memberConverter.fromDTOToDetailVO(memberDTO);
            return ResponseEntity.ok(detailVO);
        } catch (CommonException e) {
            log.error("프로필 상세 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 상세 조회 중 오류 발생");
        }
    }
}
