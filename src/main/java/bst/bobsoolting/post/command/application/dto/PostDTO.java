package bst.bobsoolting.post.command.application.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDTO {
    private Long postId;
    private String category;  // Enum을 문자열로 전달 (Converter에서 변환)
    private String title;
    private String content;
    private String images;
    private Integer maxParticipants;
    private String participants;
    private String recruitmentStatus;
    private LocalDate date;
    private String location;
    private Boolean postStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberId;
}
