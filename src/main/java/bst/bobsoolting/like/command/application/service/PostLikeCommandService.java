package bst.bobsoolting.like.command.application.service;

import jakarta.transaction.Transactional;

public interface PostLikeCommandService {
    @Transactional
    void likePost(Long postId, String kakaoId);
}
