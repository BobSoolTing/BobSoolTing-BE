package bst.bobsoolting.member.command.domain.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "RequestUpdateProfileVO", description = "회원 프로필 수정 요청 정보")
public class RequestUpdateProfileVO {

    @Schema(description = "변경할 프로필 이미지 URL", example = "https://example.com/new-profile.jpg")
    @JsonProperty("profile_image")
    private String profileImage;

    @Schema(description = "변경할 닉네임", example = "김철수")
    @JsonProperty("nickname")
    private String nickname;

    @Schema(description = "변경할 학과", example = "전자공학과")
    @JsonProperty("department")
    private String department;
}
