package bst.bobsoolting.member.command.application.controller.docs;

import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API", description = "회원 관리 관련 API 문서")
@RequestMapping("api/member")
public interface MemberCommandControllerDocs {

    @Operation(summary = "신규 회원 기본 정보 저장", description = "신규 회원의 기본 정보를 저장하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신규 회원 기본 정보 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/basic-info")
    ResponseEntity<String> registerBasicInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "추가 회원가입 정보 등록", description = "추가 회원가입 정보를 등록하는 API (신규 회원 추가 정보 입력)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/complete")
    ResponseEntity<ResponseCreateMemberVO> completeRegistration(@RequestBody RequestAdditionalRegisterVO info, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "회원 정보 수정", description = "회원 프로필을 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/profile")
    ResponseEntity<ResponseProfileVO> updateProfile(@RequestBody RequestUpdateProfileVO updateInfo, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
