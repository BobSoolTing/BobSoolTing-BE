package bst.bobsoolting.post.command.domain.vo.request;

import bst.bobsoolting.post.command.domain.aggregate.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestCreatePostVO {
    @Schema(description = "카테고리 (FOOD, DRINK, MEETING)", example = "FOOD")
    private Category category;

    @Schema(description = "제목", example = "저녁 드실 분!")
    private String title;

    @Schema(description = "내용", example = "3월 15일에 저녁 드실 분 구합니다.")
    private String content;

    @Schema(description = "최대 참가자 수", example = "5")
    @JsonProperty("max_participants")
    private Integer maxParticipants;

    @Schema(description = "활동 날짜", example = "2025-03-15")
    private LocalDate date;

    @Schema(description = "장소", example = "서울 강남구")
    private String location;
}
