package bst.bobsoolting.comment.command.application.controller.docs;

import bst.bobsoolting.comment.command.domain.vo.request.RequestCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.request.RequestUpdateCommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "댓글 관련 API 문서")
public interface CommentCommandControllerDocs {

    @Operation(summary = "댓글 등록", description = "댓글을 등록하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    ResponseEntity<?> createComment(@RequestBody RequestCreateCommentVO request, @AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "댓글 수정", description = "댓글을 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody RequestUpdateCommentVO request, @AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal OAuth2User user);
}
