package bst.bobsoolting.comment.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "RequestUpdateCommentVO", description = "댓글 수정 요청 객체")
public class RequestUpdateCommentVO {

    @Schema(description = "수정할 댓글 내용", example = "이 부분이 정말 공감됩니다.")
    @JsonProperty("comment_content")
    private String commentContent;
}
