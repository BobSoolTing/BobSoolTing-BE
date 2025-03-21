package bst.bobsoolting.comment.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponseCommentVO", description = "댓글 응답 객체")
public class ResponseCommentVO {

    @Schema(description = "댓글 ID", example = "1L")
    @JsonProperty("comment_id")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "이거 정말 좋은 글이네요!")
    @JsonProperty("comment_content")
    private String commentContent;

    @Schema(description = "댓글 작성 시간", example = "2024-03-04T14:30:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 ID", example = "1L")
    @JsonProperty("post_id")
    private Long postId;

    @Schema(description = "작성자 ID", example = "user1234")
    @JsonProperty("member_id")
    private String memberId;
}
