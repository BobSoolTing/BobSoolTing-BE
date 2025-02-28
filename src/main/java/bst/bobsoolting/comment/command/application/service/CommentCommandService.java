package bst.bobsoolting.comment.command.application.service;

import bst.bobsoolting.comment.command.application.dto.CommentDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CommentCommandService {
    @Transactional
    CommentDTO createComment(CommentDTO commentDTO, OAuth2User user);

    @Transactional
    CommentDTO updateComment(CommentDTO commentDTO, OAuth2User user);

    @Transactional
    CommentDTO deleteComment(Long commentId, OAuth2User user);
}
