package bst.bobsoolting.like.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponsePostLikeVO", description = "게시글 좋아요 응답 객체")
public class ResponsePostLikeVO {

    @Schema(description = "좋아요 ID", example = "1L")
    @JsonProperty("like_id")
    private Long likeId;

    @Schema(description = "좋아요 한 시각", example = "2024-03-04T14:30:00")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 ID", example = "1L")
    @JsonProperty("post_id")
    private Long postId;

    @Schema(description = "좋아요 등록한 사용자 ID", example = "20250315-UUID")
    @JsonProperty("member_id")
    private String memberId;
}
