package bst.bobsoolting.reply.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateReplyVO {
    @JsonProperty("reply_content")
    private String replyContent;
}
