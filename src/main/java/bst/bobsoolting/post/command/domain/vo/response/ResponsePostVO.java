package bst.bobsoolting.post.command.domain.vo.response;

import bst.bobsoolting.post.command.domain.aggregate.Category;
import bst.bobsoolting.post.command.domain.aggregate.RecruitmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "ResponsePostVO", description = "게시글 조회 응답 객체")
public class ResponsePostVO {

    @Schema(description = "게시글 ID", example = "1")
    @JsonProperty("post_id")
    private Long postId;

    @Schema(description = "카테고리", example = "FOOD")
    private Category category;

    @Schema(description = "제목", example = "저녁 드실 분!")
    private String title;

    @Schema(description = "내용", example = "3월 15일에 저녁 드실 분 구합니다.")
    private String content;

    @Schema(description = "최대 참가자 수", example = "5")
    @JsonProperty("max_participants")
    private Integer maxParticipants;

    @Schema(description = "참여자 목록", example = "[\"user1\", \"user2\"]")
    private List<String> participants;

    @Schema(description = "모집 상태", example = "OPEN")
    private RecruitmentStatus recruitmentStatus;

    @Schema(description = "활동 날짜", example = "2025-03-15")
    private LocalDate date;

    @Schema(description = "장소", example = "서울 강남구")
    private String location;

    @Schema(description = "작성자 ID", example = "20250315-UUID")
    @JsonProperty("member_id")
    private String memberId;
}
