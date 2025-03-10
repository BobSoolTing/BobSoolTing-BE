package bst.bobsoolting.post.command.vo.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdatePostVO {
	// 업데이트할 게시글의 식별자 (수정 대상 확인용)
	@JsonProperty("postId")
	private Long postId;
	    
	// 사용자가 수정 가능한 필드들
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("images")
	private String images;            // JSON 문자열 형태로 처리할 수도 있습니다.
	
	@JsonProperty("maxParticipants")
	private Integer maxParticipants;  // 수정할 최대 참여 인원
	
	@JsonProperty("recruitmentStatus")
	private String recruitmentStatus;
	
	@JsonProperty("date")
	private LocalDate date;
	
	@JsonProperty("location")
	private String location;
	    
}
