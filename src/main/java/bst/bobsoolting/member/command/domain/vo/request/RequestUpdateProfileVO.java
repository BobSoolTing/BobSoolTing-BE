package bst.bobsoolting.member.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestUpdateProfileVO {
    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("department")
    private String department;
}
