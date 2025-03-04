package bst.bobsoolting.member.command.domain.vo.response;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "회원 상세 정보 응답 객체")
public class ResponseDetailVO {

    @Schema(description = "닉네임", example = "홍길동")
    @JsonProperty("nickname")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_image")
    private String profileImage;

    @Schema(description = "성별", example = "MALE")
    @JsonProperty("gender")
    private MemberGender gender;

    @Schema(description = "생년월일", example = "1995-05-20")
    @JsonProperty("birth")
    private Date birth;

    @Schema(description = "대학교", example = "서울대학교")
    @JsonProperty("university")
    private String university;

    @Schema(description = "학과", example = "컴퓨터공학과")
    @JsonProperty("department")
    private String department;

    @Schema(description = "학번", example = "201901234")
    @JsonProperty("student_number")
    private Integer studentNumber;

    @Schema(description = "평점", example = "4.5")
    @JsonProperty("rating")
    private Float rating;
}
