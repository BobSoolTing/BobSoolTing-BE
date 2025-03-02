package bst.bobsoolting.reply.command.application.mapper;

import bst.bobsoolting.reply.command.application.dto.ReplyDTO;
import bst.bobsoolting.reply.command.domain.aggregate.entity.Reply;
import bst.bobsoolting.reply.command.domain.vo.request.RequestCreateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.request.RequestUpdateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.response.ResponseCreateReplyVO;
import bst.bobsoolting.reply.command.domain.vo.response.ResponseUpdateReplyVO;
import org.springframework.stereotype.Component;

@Component
public class ReplyConverter {

    public ReplyDTO fromCreateVOToDTO(RequestCreateReplyVO request) {
        return ReplyDTO.builder()
                .replyContent(request.getReplyContent())
                .commentId(request.getCommentId())
                .build();
    }

    public ResponseCreateReplyVO fromDTOToCreateVO(ReplyDTO savedReplyDTO) {
        return ResponseCreateReplyVO.builder()
                .replyId(savedReplyDTO.getReplyId())
                .replyContent(savedReplyDTO.getReplyContent())
                .createdAt(savedReplyDTO.getCreatedAt().toString())
                .build();
    }

    public ReplyDTO fromUpdateVOToDTO(RequestUpdateReplyVO request) {
        return ReplyDTO.builder()
                .replyContent(request.getReplyContent())
                .build();
    }

    public ResponseUpdateReplyVO fromDTOToUpdateVO(ReplyDTO updatedReply) {
        return ResponseUpdateReplyVO.builder()
                .replyId(updatedReply.getReplyId())
                .replyContent(updatedReply.getReplyContent())
                .build();
    }

    public Reply fromDTOToEntity(ReplyDTO replyDTO) {
        return Reply.builder()
                .replyId(replyDTO.getReplyId())
                .replyContent(replyDTO.getReplyContent())
                .replyStatus(replyDTO.getReplyStatus())
                .createdAt(replyDTO.getCreatedAt())
                .commentId(replyDTO.getCommentId())
                .memberId(replyDTO.getMemberId())
                .build();
    }

    public ReplyDTO fromEntityToDTO(Reply savedReply) {
        return ReplyDTO.builder()
                .replyId(savedReply.getReplyId())
                .replyContent(savedReply.getReplyContent())
                .replyStatus(savedReply.getReplyStatus())
                .createdAt(savedReply.getCreatedAt())
                .commentId(savedReply.getCommentId())
                .memberId(savedReply.getMemberId())
                .build();
    }
}
