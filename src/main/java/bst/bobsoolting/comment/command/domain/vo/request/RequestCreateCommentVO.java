package bst.bobsoolting.comment.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(description = "댓글 생성 요청 객체")
public class RequestCreateCommentVO {

    @Schema(description = "댓글 내용", example = "이거 정말 좋은 글이네요!")
    @JsonProperty("comment_content")
    private String commentContent;
}
