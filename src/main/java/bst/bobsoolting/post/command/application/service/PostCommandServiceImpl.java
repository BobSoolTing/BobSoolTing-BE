package bst.bobsoolting.post.command.application.service;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.domain.aggregate.Post;
import bst.bobsoolting.post.command.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        postDTO.setPostStatus(true);
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setUpdatedAt(LocalDateTime.now());

        Post post = PostConverter.toEntity(postDTO);
        postRepository.createPost(post);

        return PostConverter.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO updatePost(PostDTO postDTO) {
        // 기존 엔티티 조회
        Post existing = postRepository.findByPostId(postDTO.getPostId());
        if(existing == null) {
            throw new RuntimeException("Post not found with id: " + postDTO.getPostId());
        }
        // 필요한 필드만 업데이트 (category 와 postStatus는 변경하지 않음)
        existing.update(
            postDTO.getTitle(),
            postDTO.getContent(),
            postDTO.getImages(),
            postDTO.getMaxParticipants(),
            postDTO.getRecruitmentStatus(),
            postDTO.getDate(),
            postDTO.getLocation()
        );
        postRepository.updatePost(existing);
        return PostConverter.toDTO(existing);
    }

    @Override
    @Transactional
    public void softDeletePost(Long postId) {
        postRepository.softDeletePost(postId);
    }
}

