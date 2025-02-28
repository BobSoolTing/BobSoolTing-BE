package bst.bobsoolting.member.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProfileVO {
    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("profile_image")
    private String profileImage;
}
