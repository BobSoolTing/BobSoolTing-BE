package bst.bobsoolting.reply.command.application.controller.docs;

import bst.bobsoolting.reply.command.domain.vo.request.RequestCreateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.request.RequestUpdateReplyVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "대댓글 API", description = "대댓글 관련 API 문서")
@RequestMapping("api/reply")
public interface ReplyCommandControllerDocs {

    @Operation(summary = "대댓글 등록", description = "대댓글을 등록하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대댓글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    ResponseEntity<?> createReply(@RequestBody RequestCreateReplyVO request, @AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "대댓글 수정", description = "대댓글을 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PatchMapping("/{replyId}")
    ResponseEntity<?> updateReply(@PathVariable Long replyId, @RequestBody RequestUpdateReplyVO request, @AuthenticationPrincipal OAuth2User user);

    @Operation(summary = "대댓글 삭제", description = "대댓글을 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PatchMapping("/deactivate/{replyId}")
    ResponseEntity<?> deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal OAuth2User user);
}
