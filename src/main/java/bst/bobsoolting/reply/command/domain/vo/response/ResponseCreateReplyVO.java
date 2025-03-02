package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseCreateReplyVO {
    @JsonProperty("reply_id")
    private Long replyId;

    @JsonProperty("reply_content")
    private String replyContent;

    @JsonProperty("created_at")
    private String createdAt;
}
