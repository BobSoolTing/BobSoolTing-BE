package bst.bobsoolting.post.command.application.service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import bst.bobsoolting.member.command.application.service.MemberCommandServiceImpl;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.domain.aggregate.Post;
import bst.bobsoolting.post.command.domain.repository.PostRepository;
import bst.bobsoolting.post.command.vo.request.RequestUpdatePostVO;
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
    private final PostConverter postConverter; //

    @Override
    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        postDTO.setPostStatus(true);
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setUpdatedAt(LocalDateTime.now());

        // static 메서드 대신 인스턴스 메서드 호출
        Post post = postConverter.toEntity(postDTO);
        postRepository.save(post);

        return postConverter.toDTO(post);
    }

    @Override
    @Transactional
    public PostDTO updatePost(RequestUpdatePostVO updateVO) {
        Post existing = postMapper.findByPostId(updateVO.getPostId());
        if (existing == null) {
            throw new RuntimeException("Post not found with id: " + updateVO.getPostId());
        }

        Post updatedPost = Post.builder()
                .postId(existing.getPostId())
                .category(existing.getCategory())
                .title(updateVO.getTitle() != null ? updateVO.getTitle() : existing.getTitle())
                .content(updateVO.getContent() != null ? updateVO.getContent() : existing.getContent())
                .images(updateVO.getImages() != null ? updateVO.getImages() : existing.getImages())
                .maxParticipants(updateVO.getMaxParticipants() != null ? updateVO.getMaxParticipants() : existing.getMaxParticipants())
                .participants(existing.getParticipants()) // 참여자 목록은 그대로 유지
                .recruitmentStatus(updateVO.getRecruitmentStatus() != null ? updateVO.getRecruitmentStatus() : existing.getRecruitmentStatus())
                .date(updateVO.getDate() != null ? updateVO.getDate() : existing.getDate())
                .location(updateVO.getLocation() != null ? updateVO.getLocation() : existing.getLocation())
                .postStatus(existing.getPostStatus()) // 이 값은 변경하지 않는다고 가정
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .memberId(existing.getMemberId())
                .build();

        postRepository.save(updatedPost);

        return postConverter.toDTO(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post existing = postMapper.findByPostId(postId);
        if(existing == null) {
            throw new RuntimeException("Post not found with id: " + postId);
        }

        Post updatedPost = Post.builder()
                .postId(existing.getPostId())
                .category(existing.getCategory())
                .title(existing.getTitle())
                .content(existing.getContent())
                .images(existing.getImages())
                .maxParticipants(existing.getMaxParticipants())
                .participants(existing.getParticipants())
                .recruitmentStatus(existing.getRecruitmentStatus())
                .date(existing.getDate())
                .location(existing.getLocation())
                .postStatus(false) // 소프트 딜리트를 위해 false로 설정
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .memberId(existing.getMemberId())
                .build();

        postRepository.save(updatedPost);
    }
}
