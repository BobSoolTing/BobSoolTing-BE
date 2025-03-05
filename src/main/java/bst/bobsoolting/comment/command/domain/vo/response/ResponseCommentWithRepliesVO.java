package bst.bobsoolting.comment.command.domain.vo.response;

import bst.bobsoolting.reply.command.domain.vo.response.ResponseReplyVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponseCommentWithRepliesVO", description = "댓글과 대댓글 응답 객체")
public class ResponseCommentWithRepliesVO {

    @Schema(description = "댓글 정보")
    @JsonProperty("comment")
    private ResponseCommentVO comment;

    @Schema(description = "대댓글 리스트", example = "[...]")
    @JsonProperty("replies")
    private List<ResponseReplyVO> replies;
}
