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
    @Schema(description = "카테고리 (BOB, SOOL, TING)", example = "BOB")
    private Category category;

    @Schema(description = "제목", example = "스터디 모집합니다!")
    private String title;

    @Schema(description = "내용", example = "자바 스터디 같이 하실 분?")
    private String content;

    @Schema(description = "이미지 리스트 (JSON 형식)", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> images;

    @Schema(description = "최대 참가자 수", example = "5")
    @JsonProperty("max_participants")
    private Integer maxParticipants;

    @Schema(description = "참여자 목록 (JSON 형식)", example = "[\"user1\", \"user2\"]")
    private List<String> participants;

    @Schema(description = "모집 상태", example = "RECRUITING")
    @JsonProperty("recruitment_status")
    private String recruitmentStatus;

    @Schema(description = "활동 날짜", example = "2025-03-15")
    private LocalDate date;

    @Schema(description = "장소", example = "서울 강남구")
    private String location;
}
