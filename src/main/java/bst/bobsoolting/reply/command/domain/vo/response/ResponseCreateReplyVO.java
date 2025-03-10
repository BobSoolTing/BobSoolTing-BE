package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponseCreateReplyVO", description = "대댓글 생성 응답 객체")
public class ResponseCreateReplyVO {

    @Schema(description = "생성된 대댓글 ID", example = "2001")
    @JsonProperty("reply_id")
    private Long replyId;

    @Schema(description = "대댓글 내용", example = "맞아요, 저도 그렇게 생각해요!")
    @JsonProperty("reply_content")
    private String replyContent;

    @Schema(description = "대댓글 생성 시간 (ISO 8601 형식)", example = "2024-03-04T14:30:00")
    @JsonProperty("created_at")
    private String createdAt;
}
