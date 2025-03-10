package bst.bobsoolting.member.command.application.controller;

import bst.bobsoolting.common.exception.CommonException;
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

import java.util.Map;

@RestController
@RequestMapping("api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberCommandController {

    private final MemberCommandService memberCommandService;
    private final MemberConverter memberConverter;

    @PostMapping("/complete-registration")
    public ResponseEntity<?> completeRegistration(@RequestBody RequestAdditionalRegisterVO info) {
        log.info("추가 회원가입 정보 수신: {}", info);
        MemberDTO newMemberDTO = memberConverter.fromAdditionalVOToEntity(info);
        MemberDTO member = memberCommandService.createMember(newMemberDTO);
        ResponseCreateMemberVO memberVO = memberConverter.fromEntityToCreateVO(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberVO);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal OAuth2User user, @RequestBody RequestUpdateProfileVO updateInfo) {
        log.info("프로필 수정 요청: {} -> {}", user.getAttribute("id"), updateInfo);
        MemberDTO updatedMember = memberCommandService.updateMemberProfile(user, updateInfo);
        ResponseProfileVO responseProfileVO = memberConverter.fromEntityToProfileVO(updatedMember);
        return ResponseEntity.ok(responseProfileVO);
    }

    @PostMapping("/auth/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        log.info("카카오 로그인 요청. 인가 코드: {}", code);
        try {
            String accessToken = memberCommandService.getKakaoAccessToken(code);
            MemberDTO member = memberCommandService.getKakaoUserInfo(accessToken);
            ResponseCreateMemberVO response = memberConverter.fromEntityToCreateVO(member);

            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("카카오 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카카오 로그인 처리 중 오류 발생");
        }
    }
}
