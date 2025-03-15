package bst.bobsoolting.post.command.domain.vo.request;

import bst.bobsoolting.post.command.domain.aggregate.RecruitmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpdatePostVO {
	@Schema(description = "제목", example = "수정된 제목입니다.")
	private String title;

	@Schema(description = "내용", example = "수정된 내용입니다.")
	private String content;

	@Schema(description = "최대 참가자 수", example = "10")
	private Integer maxParticipants;

	@Schema(description = "모집 상태", example = "CLOSED")
	private RecruitmentStatus recruitmentStatus;

	@Schema(description = "활동 날짜", example = "2025-04-01")
	private LocalDate date;

	@Schema(description = "장소", example = "서울 마포구")
	private String location;
}
