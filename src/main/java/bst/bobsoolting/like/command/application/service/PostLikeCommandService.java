package bst.bobsoolting.like.command.application.service;

import bst.bobsoolting.like.command.application.dto.PostLikeDTO;
import jakarta.transaction.Transactional;

public interface PostLikeCommandService {
    @Transactional
    PostLikeDTO likePost(Long postId, String kakaoId);
}
