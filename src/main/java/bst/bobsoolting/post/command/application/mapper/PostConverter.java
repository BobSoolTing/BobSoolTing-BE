package bst.bobsoolting.post.command.application.mapper;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.Category;
import bst.bobsoolting.post.command.domain.aggregate.entity.Post;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    // DTO -> Entity 변환
    public Post fromVOToEntity(RequestCreatePostVO request, String memberId) {
        if (request == null) return null;
        return Post.builder()
                .category(request.getCategory() != null ? request.getCategory() : null)
                .title(request.getTitle())
                .content(request.getContent())
                .images(request.getImages())
                .maxParticipants(request.getMaxParticipants())
                .participants(request.getParticipants())
                .recruitmentStatus(request.getRecruitmentStatus())
                .date(request.getDate())
                .location(request.getLocation())
                .postStatus(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .memberId(memberId)
                .build();
    }

    // Entity -> DTO 변환
    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return PostDTO.builder()
                .postId(post.getPostId())
                .category(post.getCategory() != null ? post.getCategory().name() : null)
                .title(post.getTitle())
                .content(post.getContent())
                .images(post.getImages())
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
