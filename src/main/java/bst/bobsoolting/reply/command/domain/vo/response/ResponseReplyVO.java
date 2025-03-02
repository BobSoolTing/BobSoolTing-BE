package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseReplyVO {
    @JsonProperty("reply_id")
    private Long replyId;

    @JsonProperty("reply_content")
    private String replyContent;

    @JsonProperty("member_id")
    private String memberId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
