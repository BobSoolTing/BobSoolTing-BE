package bst.bobsoolting.like.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostLikeDTO {
    private Long likeId;
    private LocalDateTime createdAt;
    private Long postId;
    private String memberId;
}
