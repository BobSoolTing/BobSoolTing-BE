package bst.bobsoolting.reply.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "RequestCreateReplyVO", description = "대댓글 생성 요청 객체")
public class RequestCreateReplyVO {

    @Schema(description = "대댓글 내용", example = "저도 그렇게 생각해요!")
    @JsonProperty("reply_content")
    private String replyContent;

    @Schema(description = "댓글 ID (대댓글이 달릴 부모 댓글 ID)", example = "1001")
    @JsonProperty("comment_id")
    private Long commentId;
}
