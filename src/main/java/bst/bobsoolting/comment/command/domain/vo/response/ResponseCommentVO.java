package bst.bobsoolting.comment.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseCommentVO {
    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("comment_content")
    private String commentContent;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("member_id")
    private String memberId;
}
