package bst.bobsoolting.member.command.application.controller;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.member.command.application.controller.docs.MemberCommandControllerDocs;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.application.service.MemberCommandService;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberCommandController implements MemberCommandControllerDocs {

    private final MemberCommandService memberCommandService;
    private final MemberConverter memberConverter;

    @PostMapping("/basic-info")
    public ResponseEntity<String> registerBasicInfo(@AuthenticationPrincipal OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        log.info("신규 회원 기본 정보 저장 요청: {}", kakaoId);
        try {
            memberCommandService.createBasicMember(kakaoId);
            return ResponseEntity.ok("신규 회원 기본 정보 저장 완료");
        } catch (CommonException e) {
            log.error("회원 기본 정보 저장 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 기본 정보 저장 중 오류 발생");
        }
    }

    @PatchMapping("/complete")
    public ResponseEntity<ResponseCreateMemberVO> completeRegistration(@AuthenticationPrincipal OAuth2User user, @RequestBody RequestAdditionalRegisterVO info) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        log.info("추가 회원가입 정보 수신: kakaoId={}, 추가 정보={}", kakaoId, info);

        try {
            MemberDTO updatedMember = memberCommandService.updateMemberAdditionalInfo(kakaoId, info);
            ResponseCreateMemberVO response = memberConverter.fromEntityToCreateVO(updatedMember);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("추가 회원가입 정보 업데이트 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ResponseProfileVO> updateProfile(@AuthenticationPrincipal OAuth2User user, @RequestBody RequestUpdateProfileVO updateInfo) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        log.info("프로필 수정 요청: {} -> {}", kakaoId, updateInfo);
        MemberDTO updatedMember = memberCommandService.updateMemberProfile(kakaoId, updateInfo);
        ResponseProfileVO responseProfileVO = memberConverter.fromEntityToProfileVO(updatedMember);
        return ResponseEntity.ok(responseProfileVO);
    }
}
