package bst.bobsoolting.comment.command.domain.vo.response;

import bst.bobsoolting.reply.command.domain.vo.response.ResponseReplyVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseCommentWithRepliesVO {
    @JsonProperty("comment")
    private ResponseCommentVO comment;

    @JsonProperty("replies")
    private List<ResponseReplyVO> replies;
}
