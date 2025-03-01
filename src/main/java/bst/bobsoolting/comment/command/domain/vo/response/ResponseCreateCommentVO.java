package bst.bobsoolting.comment.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCreateCommentVO {
    @JsonProperty("comment_content")
    private String commentContent;
}
