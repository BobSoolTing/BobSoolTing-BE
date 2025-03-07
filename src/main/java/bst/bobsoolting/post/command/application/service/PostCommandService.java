package bst.bobsoolting.post.command.application.service;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.vo.request.RequestUpdatePostVO;

public interface PostCommandService {

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(RequestUpdatePostVO updateVO);

    void deletePost(Long postId);
}