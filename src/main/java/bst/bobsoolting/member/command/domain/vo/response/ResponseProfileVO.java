package bst.bobsoolting.member.command.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ResponseProfileVO", description = "회원 프로필 응답 객체")
public class ResponseProfileVO {

    @Schema(description = "닉네임", example = "홍길동")
    @JsonProperty("nickname")
    private String nickname;

    @Schema(description = "평점", example = "4.5")
    @JsonProperty("rating")
    private Float rating;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_image")
    private String profileImage;
}
