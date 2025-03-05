package bst.bobsoolting.comment.command.application.controller;

import bst.bobsoolting.comment.command.application.controller.docs.CommentCommandControllerDocs;
import bst.bobsoolting.comment.command.application.dto.CommentDTO;
import bst.bobsoolting.comment.command.application.mapper.CommentConverter;
import bst.bobsoolting.comment.command.application.service.CommentCommandService;
import bst.bobsoolting.comment.command.domain.vo.request.RequestCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.request.RequestUpdateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseUpdateCommentVO;
import bst.bobsoolting.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentCommandController implements CommentCommandControllerDocs {

    private final CommentCommandService commentService;
    private final CommentConverter commentConverter;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody RequestCreateCommentVO request, @AuthenticationPrincipal OAuth2User user) {
        log.info("등록 요청된 댓글 데이터 : {}", request);
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        try {
            CommentDTO commentDTO = commentConverter.fromCreateVOToDTO(request);
            CommentDTO saveCommentDTO = commentService.createComment(commentDTO, kakaoId);
            ResponseCreateCommentVO response = commentConverter.fromDTOToCreateVO(saveCommentDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("댓글 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody RequestUpdateCommentVO request, @AuthenticationPrincipal OAuth2User user) {
        log.info("수정 요청된 commentId: {}, 데이터: {}", commentId, request);
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        try {
            CommentDTO commentDTO = commentConverter.fromUpdateVOToDTO(request);
            commentDTO.setCommentId(commentId);

            CommentDTO updatedComment = commentService.updateComment(commentDTO, kakaoId);

            ResponseUpdateCommentVO response = commentConverter.fromDTOToUpdateVO(updatedComment);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("댓글 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/deactivate/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal OAuth2User user) {
        log.info("삭제 요청된 댓글 ID : {}", commentId);
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        try {
            CommentDTO deletedComment = commentService.deleteComment(commentId, kakaoId);
            log.info("삭제된 commentId : {}, 해당 댓글의 상태 : {}", deletedComment.getCommentId(), deletedComment.getCommentStatus());
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적으로 삭제되었습니다.");
        } catch (CommonException e) {
            log.error("댓글 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}