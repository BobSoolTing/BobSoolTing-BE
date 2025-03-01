package bst.bobsoolting.comment.query.repository;

import bst.bobsoolting.comment.command.domain.aggregate.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findCommentsByPost(@Param("postId") Long postId, @Param("cursor") Long cursor);
}
