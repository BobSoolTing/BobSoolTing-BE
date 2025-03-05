package bst.bobsoolting.member.query.controller.docs;

import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "회원 조회 API", description = "회원 정보 조회 관련 API 문서")
@RequestMapping("api/member")
public interface MemberQueryControllerDocs {

    @Operation(summary = "카카오 OAuth2 로그인 성공 후 처리", description = "카카오 로그인 성공 후 사용자 정보를 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/loginSuccess")
    ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "카카오 OAuth2 로그인 실패 처리", description = "카카오 로그인 실패 시 처리")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    @GetMapping("/loginFailure")
    ResponseEntity<String> loginFailure();

    @Operation(summary = "마이페이지 조회", description = "현재 로그인한 사용자의 마이페이지 정보를 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/my-page")
    ResponseEntity<ResponseProfileVO> getMyProfile(@AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "프로필 상세 조회", description = "현재 로그인한 사용자의 상세 프로필을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/profile")
    ResponseEntity<ResponseDetailVO> getMyProfileDetails(@AuthenticationPrincipal OAuth2User user);
}
