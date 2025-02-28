package bst.bobsoolting.comment.command.application.mapper;

import bst.bobsoolting.comment.command.application.dto.CommentDTO;
import bst.bobsoolting.comment.command.domain.aggregate.entity.Comment;
import bst.bobsoolting.comment.command.domain.vo.request.RequestCreateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.request.RequestUpdateCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentVO;
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

    public CommentDTO fromEntityToDTO(Comment savedComment) {
        return CommentDTO.builder()
                .commentId(savedComment.getCommentId())
                .commentContent(savedComment.getCommentContent())
                .commentStatus(savedComment.getCommentStatus())
                .createdAt(savedComment.getCreatedAt())
                .postId(savedComment.getPostId())
                .memberId(savedComment.getMemberId())
                .build();
    }

    public ResponseCommentVO fromEntityToResponseVO(Comment comment) {
        return ResponseCommentVO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt())
                .memberId(comment.getMemberId())
                .build();
    }
}
