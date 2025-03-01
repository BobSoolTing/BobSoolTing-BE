package bst.bobsoolting.like.command.application.service;

import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface PostLikeCommandService {
    @Transactional
    void likePost(Long postId, OAuth2User user);
}
