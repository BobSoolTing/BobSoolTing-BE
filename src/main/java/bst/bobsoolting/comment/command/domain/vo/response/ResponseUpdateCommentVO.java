package bst.bobsoolting.comment.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "댓글 수정 응답 객체")
public class ResponseUpdateCommentVO {

    @Schema(description = "수정된 댓글 내용", example = "이 부분이 정말 공감됩니다.")
    @JsonProperty("comment_content")
    private String commentContent;
}
