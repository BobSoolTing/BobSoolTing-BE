package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponseUpdateReplyVO", description = "대댓글 수정 응답 객체")
public class ResponseUpdateReplyVO {

    @Schema(description = "수정된 대댓글 ID", example = "2001")
    @JsonProperty("reply_id")
    private Long replyId;

    @Schema(description = "수정된 대댓글 내용", example = "수정된 대댓글 내용입니다.")
    @JsonProperty("reply_content")
    private String replyContent;
}
