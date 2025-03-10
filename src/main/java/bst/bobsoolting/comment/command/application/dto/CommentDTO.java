package bst.bobsoolting.comment.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private Boolean commentStatus;
    private LocalDateTime createdAt;
    private Long postId;
    private String memberId;
}
