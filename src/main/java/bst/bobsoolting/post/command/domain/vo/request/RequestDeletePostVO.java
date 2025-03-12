package bst.bobsoolting.post.command.domain.vo.request;

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
	@JsonProperty("postStatus")
	private Boolean postStatus;
	
	@JsonProperty("updatedAt")
	private LocalDateTime UpdatedAt;
}
