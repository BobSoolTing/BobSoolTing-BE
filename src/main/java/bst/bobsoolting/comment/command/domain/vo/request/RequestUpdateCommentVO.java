package bst.bobsoolting.comment.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateCommentVO {
    @JsonProperty("comment_content")
    private String commentContent;
}
