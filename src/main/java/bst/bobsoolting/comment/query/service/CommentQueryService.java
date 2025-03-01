package bst.bobsoolting.comment.query.service;

import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentVO;

import java.util.List;

public interface CommentQueryService {
    List<ResponseCommentVO> getCommentsByPost(Long postId, Long cursor, int size);
}
