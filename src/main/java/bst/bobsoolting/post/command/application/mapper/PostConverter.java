package bst.bobsoolting.post.command.application.mapper;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.Category;
import bst.bobsoolting.post.command.domain.aggregate.Post;

public class PostConverter {

    // DTO -> Entity 변환
    public static Post toEntity(PostDTO dto) {
        if (dto == null) return null;
        return Post.builder()
                .postId(dto.getPostId())
                .category(dto.getCategory() != null ? Category.valueOf(dto.getCategory()) : null)
                .title(dto.getTitle())
                .content(dto.getContent())
                .images(dto.getImages())
                .maxParticipants(dto.getMaxParticipants())
                .participants(dto.getParticipants())
                .recruitmentStatus(dto.getRecruitmentStatus())
                .date(dto.getDate())
                .location(dto.getLocation())
                .postStatus(dto.getPostStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .memberId(dto.getMemberId())
                .build();
    }

    // Entity -> DTO 변환
    public static PostDTO toDTO(Post post) {
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
