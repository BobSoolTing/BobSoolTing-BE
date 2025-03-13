package bst.bobsoolting.post.command.application.service;

import bst.bobsoolting.post.command.domain.aggregate.RecruitmentStatus;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import lombok.extern.slf4j.Slf4j;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.domain.aggregate.entity.Post;
import bst.bobsoolting.post.command.domain.repository.PostRepository;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import bst.bobsoolting.post.query.repository.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostConverter postConverter;

    @Override
    @Transactional
    public PostDTO createPost(RequestCreatePostVO request, String memberId) {
        Post post = postConverter.fromCreateVOToEntity(request, memberId);
        postRepository.save(post);

        return postConverter.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO updatePost(String memberId, Long postId, RequestUpdatePostVO updateVO) {
        Post existingPost = postMapper.findByPostId(postId);
        if (existingPost == null) throw new RuntimeException("Post not found with id: " + postId);
        if (!existingPost.getMemberId().equals(memberId)) throw new RuntimeException("You are not authorized to update this post.");

        Post updatedPost = postConverter.fromUpdateVOToEntity(existingPost, updateVO);

        postRepository.save(updatedPost);
        return postConverter.toDTO(updatedPost);
    }

    @Override
    @Transactional
    public void updateRecruitmentStatus(String memberId, Long postId) {
        Post existing = postMapper.findByPostId(postId);
        if (existing == null) throw new RuntimeException("Post not found with id: " + postId);
        if (!existing.getMemberId().equals(memberId)) throw new RuntimeException("You are not authorized to update this post.");

        existing.setRecruitmentStatus(RecruitmentStatus.CLOSED);
        existing.setUpdatedAt(LocalDateTime.now());

        postRepository.save(existing);
    }

    @Override
    @Transactional
    public void deletePost(String memberId, Long postId) {
        Post existing = postMapper.findByPostId(postId);
        if (existing == null) throw new RuntimeException("Post not found with id: " + postId);
        if (!existing.getMemberId().equals(memberId)) throw new RuntimeException("You are not authorized to delete this post.");

        existing.setPostStatus(false);
        existing.setUpdatedAt(LocalDateTime.now());

        postRepository.save(existing);
    }
}
