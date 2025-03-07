package bst.bobsoolting.comment.query.controller.docs;

import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentWithRepliesVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글 조회 API", description = "댓글 조회 관련 API 문서")
public interface CommentQueryControllerDocs {

    @Operation(summary = "특정 게시글의 댓글 및 대댓글 조회", description = "특정 게시글의 댓글과 대댓글을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    ResponseEntity<List<ResponseCommentWithRepliesVO>> getCommentsByPost(@PathVariable Long postId, @RequestParam(required = false) Long cursor, @RequestParam(defaultValue = "10") int size);
}
