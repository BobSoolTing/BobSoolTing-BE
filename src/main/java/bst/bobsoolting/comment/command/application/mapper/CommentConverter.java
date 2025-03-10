package bst.bobsoolting.comment.command.application.mapper;

import bst.bobsoolting.comment.command.application.dto.CommentDTO;
import bst.bobsoolting.comment.command.domain.aggregate.entity.Comment;
import bst.bobsoolting.reply.command.domain.aggregate.entity.Reply;
import bst.bobsoolting.comment.command.domain.vo.request.RequestCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.request.RequestUpdateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentVO;
import bst.bobsoolting.reply.command.domain.vo.response.ResponseReplyVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseUpdateCommentVO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentDTO fromCreateVOToDTO(RequestCreateCommentVO request) {
        return CommentDTO.builder()
                .commentContent(request.getCommentContent())
                .build();
    }

    public ResponseCreateCommentVO fromDTOToCreateVO(CommentDTO saveCommentDTO) {
        return ResponseCreateCommentVO.builder()
                .commentContent(saveCommentDTO.getCommentContent())
                .build();
    }

    public CommentDTO fromUpdateVOToDTO(RequestUpdateCommentVO request) {
        return CommentDTO.builder()
                .commentContent(request.getCommentContent())
                .build();
    }

    public ResponseUpdateCommentVO fromDTOToUpdateVO(CommentDTO updatedComment) {
        return ResponseUpdateCommentVO.builder()
                .commentContent(updatedComment.getCommentContent())
                .build();
    }

    public Comment fromDTOToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .commentId(commentDTO.getCommentId())
                .commentContent(commentDTO.getCommentContent())
                .commentStatus(commentDTO.getCommentStatus())
                .createdAt(commentDTO.getCreatedAt())
                .postId(commentDTO.getPostId())
                .memberId(commentDTO.getMemberId())
                .build();
    }

    public CommentDTO fromEntityToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .commentStatus(comment.getCommentStatus())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPostId())
                .memberId(comment.getMemberId())
                .build();
    }

    public ResponseCommentVO fromEntityToResponseCommentVO(Comment comment) {
        return ResponseCommentVO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt())
                .memberId(comment.getMemberId())
                .build();
    }

    public ResponseReplyVO fromEntityToResponseReplyVO(Reply reply) {
        return ResponseReplyVO.builder()
                .replyId(reply.getReplyId())
                .replyContent(reply.getReplyContent())
                .createdAt(reply.getCreatedAt())
                .memberId(reply.getMemberId())
                .build();
    }
}
