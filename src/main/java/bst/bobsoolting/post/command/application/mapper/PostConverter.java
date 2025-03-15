package bst.bobsoolting.post.command.application.mapper;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.RecruitmentStatus;
import bst.bobsoolting.post.command.domain.aggregate.entity.Post;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostConverter {

    // DTO -> Entity 변환
    public Post fromCreateVOToEntity(RequestCreatePostVO request, String memberId) {
        if (request == null) return null;

        return Post.builder()
                .category(request.getCategory() != null ? request.getCategory() : null)
                .title(request.getTitle())
                .content(request.getContent())
                .maxParticipants(request.getMaxParticipants())
                .participants(List.of())
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .date(request.getDate())
                .location(request.getLocation())
                .postStatus(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .memberId(memberId)
                .build();
    }

    public Post fromUpdateVOToEntity(Post existingPost, RequestUpdatePostVO updateVO) {
        if (existingPost == null) return null;

        return Post.builder()
                .postId(existingPost.getPostId())
                .category(existingPost.getCategory())
                .title(updateVO.getTitle() != null ? updateVO.getTitle() : existingPost.getTitle())
                .content(updateVO.getContent() != null ? updateVO.getContent() : existingPost.getContent())
                .maxParticipants(updateVO.getMaxParticipants() != null ? updateVO.getMaxParticipants() : existingPost.getMaxParticipants())
                .participants(existingPost.getParticipants())
                .recruitmentStatus(updateVO.getRecruitmentStatus() != null ? updateVO.getRecruitmentStatus() : existingPost.getRecruitmentStatus())
                .date(updateVO.getDate() != null ? updateVO.getDate() : existingPost.getDate())
                .location(updateVO.getLocation() != null ? updateVO.getLocation() : existingPost.getLocation())
                .postStatus(existingPost.getPostStatus())
                .createdAt(existingPost.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .memberId(existingPost.getMemberId())
                .build();
    }

    // Entity -> DTO 변환
    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return PostDTO.builder()
                .postId(post.getPostId())
                .category(post.getCategory() != null ? post.getCategory() : null)
                .title(post.getTitle())
                .content(post.getContent())
                .maxParticipants(post.getMaxParticipants())
                .participants(post.getParticipants())
                .recruitmentStatus(post.getRecruitmentStatus())
                .date(post.getDate())
                .location(post.getLocation())
                .postStatus(post.getPostStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .memberId(post.getMemberId())
                .build();
    }
}
