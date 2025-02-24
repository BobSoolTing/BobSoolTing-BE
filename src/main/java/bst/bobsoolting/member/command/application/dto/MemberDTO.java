package bst.bobsoolting.member.command.application.dto;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDTO {
    private Long memberId;
    private String loginId;
    private String password;
    private String nickname;
    private String phone;
    private MemberGender gender;
    private Date birth;
    private String university;
    private String department;
    private Integer studentNumber;
    private Float rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
