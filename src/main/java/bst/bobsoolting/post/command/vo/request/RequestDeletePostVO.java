package bst.bobsoolting.post.command.vo.request;

import java.time.LocalDateTime;

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
public class RequestDeletePostVO {
	// 업데이트할 게시글의 식별자 (수정 대상 확인용)
	@JsonProperty("postId")
	private Long postId;
	    
	// 사용자가 수정 가능한 필드들
	@JsonProperty("postStatus")
	private Boolean postStatus;
	
	@JsonProperty("UpdatedAt")
	private LocalDateTime UpdatedAt;
}
