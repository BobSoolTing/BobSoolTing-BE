package bst.bobsoolting.reply.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "RequestUpdateReplyVO", description = "대댓글 수정 요청 객체")
public class RequestUpdateReplyVO {

    @Schema(description = "수정할 대댓글 내용", example = "의견이 바뀌었어요!")
    @JsonProperty("reply_content")
    private String replyContent;
}
