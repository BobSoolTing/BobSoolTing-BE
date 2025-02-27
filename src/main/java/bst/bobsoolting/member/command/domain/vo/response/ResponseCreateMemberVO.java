package bst.bobsoolting.member.command.domain.vo.response;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseCreateMemberVO {
    @JsonProperty("member_id")
    private String memberId;

    @JsonProperty("kakao_id")
    private String kakaoId;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("phone")
    private String phone;

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

    @JsonProperty("member_role")
    private MemberRole memberRole;
}
