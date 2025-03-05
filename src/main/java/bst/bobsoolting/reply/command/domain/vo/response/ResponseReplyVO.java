package bst.bobsoolting.reply.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponseReplyVO", description = "대댓글 응답 객체")
public class ResponseReplyVO {

    @Schema(description = "대댓글 ID", example = "2001")
    @JsonProperty("reply_id")
    private Long replyId;

    @Schema(description = "대댓글 내용", example = "저도 그렇게 생각해요!")
    @JsonProperty("reply_content")
    private String replyContent;

    @Schema(description = "작성자 ID", example = "user5678")
    @JsonProperty("member_id")
    private String memberId;

    @Schema(description = "대댓글 작성 시간", example = "2024-03-04T14:30:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
