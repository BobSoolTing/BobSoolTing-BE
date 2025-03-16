package bst.bobsoolting.post.command.application.controller.docs;

import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import bst.bobsoolting.post.command.domain.vo.response.ResponseCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.response.ResponseUpdatePostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "게시글 API", description = "게시글 생성, 수정, 삭제 관련 API")
@RequestMapping("/api/post")
public interface PostCommandControllerDocs {

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다. JWT에서 memberId를 자동 추출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    ResponseEntity<?> createPost(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = RequestCreatePostVO.class))
            ) RequestCreatePostVO request
    );

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다. JWT에서 memberId를 자동 추출하고 본인 게시글인지 검증합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음"),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{postId}")
    ResponseEntity<?> updatePost(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(description = "게시글 ID", required = true) @PathVariable Long postId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = RequestUpdatePostVO.class))
            ) RequestUpdatePostVO postDTO
    );

    @Operation(summary = "게시글 모집 상태 변경", description = "게시글의 모집 상태를 '모집 완료'로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "모집 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음"),
            @ApiResponse(responseCode = "403", description = "변경 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/{postId}/recruit- status")
    ResponseEntity<Map<String, String>> updateRecruitmentStatus(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Parameter(description = "게시글 ID", required = true) @PathVariable("postId") Long postId
    );

    @Operation(summary = "게시글 삭제 (소프트 삭제)", description = "게시글을 소프트 딜리트합니다. JWT에서 memberId를 자동 추출하고 본인 게시글인지 검증합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/{postId}")
    ResponseEntity<Map<String, String>> softDeletePost(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token, // ✅ Authorization 헤더 추가
            @Parameter(description = "게시글 ID", required = true) @PathVariable("postId") Long postId
    );

}
