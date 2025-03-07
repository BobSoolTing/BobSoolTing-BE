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
@Schema(name = "ResponseCreateCommentVO", description = "댓글 생성 응답 객체")
public class ResponseCreateCommentVO {

    @Schema(description = "등록된 댓글 내용", example = "이거 정말 좋은 글이네요!")
    @JsonProperty("comment_content")
    private String commentContent;
}
