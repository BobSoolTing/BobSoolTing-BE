package bst.bobsoolting.post.command.application.dto;

import bst.bobsoolting.post.command.domain.aggregate.Category;
import bst.bobsoolting.post.command.domain.aggregate.RecruitmentStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDTO {
    private Long postId;
    private Category category;
    private String title;
    private String content;
    private Integer maxParticipants;
    private List<String> participants;
    private RecruitmentStatus recruitmentStatus;
    private LocalDate date;
    private String location;
    private Boolean postStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberId;
}
