package bst.bobsoolting.member.command.application.dto;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
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
    private String memberId;
    private String kakaoId;
    private String nickname;
    private String phone;
    private String profileImage;
    private MemberGender gender;
    private Date birth;
    private String university;
    private String department;
    private Integer studentNumber;
    private Float rating;
    private MemberRole memberRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
