package bst.bobsoolting.member.command.domain.vo.request;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestAdditionalRegisterVO {
    @JsonProperty("kakao_id")
    private String kakaoId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("gender")
    private MemberGender gender;

    @JsonProperty("birth")
    private Date birth;

    @JsonProperty("university")
    private String university;

    @JsonProperty("department")
    private String department;

    @JsonProperty("student_number")
    private Integer studentNumber;
}
