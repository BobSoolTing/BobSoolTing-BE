package bst.bobsoolting.member.command.application.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.application.service.MemberCommandService;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
