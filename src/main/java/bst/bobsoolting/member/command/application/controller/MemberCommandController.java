package bst.bobsoolting.member.command.application.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.application.service.MemberCommandService;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberCommandController {

    private final MemberCommandService memberCommandService;
    private final MemberConverter memberConverter;

    @Operation(description = "추가 회원가입 정보 등록")
    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration(@RequestBody RequestAdditionalRegisterVO info) {
        log.info("추가 회원가입 정보 수신: {}", info);
        MemberDTO newMemberDTO = memberConverter.fromAdditionalVOToEntity(info);
        MemberDTO member = memberCommandService.createMember(newMemberDTO);
        ResponseCreateMemberVO memberVO = memberConverter.fromEntityToCreateVO(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberVO);
    }

    @Operation(description = "회원 정보 수정")
    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal OAuth2User user, @RequestBody RequestUpdateProfileVO updateInfo) {
        log.info("프로필 수정 요청: {} -> {}", user.getAttribute("id"), updateInfo);
        MemberDTO updatedMember = memberCommandService.updateMemberProfile(user, updateInfo);
        ResponseProfileVO responseProfileVO = memberConverter.fromEntityToProfileVO(updatedMember);
        return ResponseEntity.ok(responseProfileVO);
    }
}
