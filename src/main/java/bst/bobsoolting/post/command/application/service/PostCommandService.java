package bst.bobsoolting.post.command.application.service;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import org.springframework.transaction.annotation.Transactional;

public interface PostCommandService {

    PostDTO createPost(RequestCreatePostVO request, String memberId);

    @Transactional
    PostDTO updatePost(String memberId, Long postId, RequestUpdatePostVO updateVO);

    @Transactional
    void updateRecruitmentStatus(String memberId, Long postId);

    @Transactional
    void deletePost(String memberId, Long postId);
}