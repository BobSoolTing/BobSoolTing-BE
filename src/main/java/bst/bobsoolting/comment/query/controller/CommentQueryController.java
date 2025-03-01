package bst.bobsoolting.comment.query.controller;

import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentWithRepliesVO;
import bst.bobsoolting.comment.query.service.CommentQueryService;
import bst.bobsoolting.common.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @Operation(description = "특정 게시글의 댓글 및 대댓글 목록 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long postId, @RequestParam(required = false) Long cursor, @RequestParam(defaultValue = "10") int size) {
        log.info("댓글 및 대댓글 조회 요청 - postId: {}, cursor: {}, size: {}", postId, cursor, size);
        try {
            List<ResponseCommentWithRepliesVO> comments = commentQueryService.getCommentsByPost(postId, cursor, size);
            return ResponseEntity.ok(comments);
        } catch (CommonException e) {
            log.error("댓글 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 조회 중 오류 발생");
        }
    }
}
