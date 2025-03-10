package bst.bobsoolting.reply.command.application.controller;

import bst.bobsoolting.reply.command.application.controller.docs.ReplyCommandControllerDocs;
import bst.bobsoolting.reply.command.application.dto.ReplyDTO;
import bst.bobsoolting.reply.command.application.mapper.ReplyConverter;
import bst.bobsoolting.reply.command.application.service.ReplyCommandService;
import bst.bobsoolting.reply.command.domain.vo.request.RequestCreateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.request.RequestUpdateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.response.ResponseCreateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.response.ResponseUpdateReplyVO;
import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyCommandController implements ReplyCommandControllerDocs {

    private final ReplyCommandService replyService;
    private final ReplyConverter replyConverter;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<?> createReply(@RequestBody RequestCreateReplyVO request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("대댓글 등록 요청: {}", request);
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        try {
            ReplyDTO replyDTO = replyConverter.fromCreateVOToDTO(request);
            ReplyDTO savedReplyDTO = replyService.createReply(replyDTO, kakaoId);
            ResponseCreateReplyVO response = replyConverter.fromDTOToCreateVO(savedReplyDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("대댓글 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<?> updateReply(@PathVariable Long replyId, @RequestBody RequestUpdateReplyVO request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("대댓글 수정 요청 - replyId: {}, 데이터: {}", replyId, request);
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        try {
            ReplyDTO replyDTO = replyConverter.fromUpdateVOToDTO(request);
            replyDTO.setReplyId(replyId);

            ReplyDTO updatedReply = replyService.updateReply(replyDTO, kakaoId);
            ResponseUpdateReplyVO response = replyConverter.fromDTOToUpdateVO(updatedReply);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("대댓글 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/deactivate/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("대댓글 삭제 요청 - replyId: {}", replyId);
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        try {
            ReplyDTO deletedReply = replyService.deleteReply(replyId, kakaoId);
            log.info("삭제된 replyId: {}, 대댓글 상태: {}", deletedReply.getReplyId(), deletedReply.getReplyStatus());
            return ResponseEntity.status(HttpStatus.OK).body("대댓글이 성공적으로 삭제되었습니다.");
        } catch (CommonException e) {
            log.error("대댓글 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
