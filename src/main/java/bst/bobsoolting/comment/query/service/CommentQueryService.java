package bst.bobsoolting.comment.query.service;

import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentWithRepliesVO;

import java.util.List;

public interface CommentQueryService {
    List<ResponseCommentWithRepliesVO> getCommentsByPost(Long postId, Long cursor, int size);
}
