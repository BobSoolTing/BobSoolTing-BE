package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseUpdateReplyVO {
    @JsonProperty("reply_id")
    private Long replyId;

    @JsonProperty("reply_content")
    private String replyContent;
}
