package bst.bobsoolting.member.command.domain.vo.response;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDetailVO {
    @JsonProperty("nickname")
    private String nickname;

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

    @JsonProperty("rating")
    private Float rating;
}
