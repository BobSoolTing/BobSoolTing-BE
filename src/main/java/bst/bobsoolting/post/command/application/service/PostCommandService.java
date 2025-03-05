package bst.bobsoolting.post.command.application.service;

import bst.bobsoolting.post.command.application.dto.PostDTO;

public interface PostCommandService {

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(PostDTO postDTO);

    void softDeletePost(Long postId);
}