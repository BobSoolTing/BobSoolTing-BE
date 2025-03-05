package bst.bobsoolting.member.command.domain.aggregate.entity;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private MemberGender gender;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "university")
    private String university;

    @Column(name = "department")
    private String department;

    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "rating", nullable = false)
    private Float rating = 0.0f;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.ROLE_USER;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(MemberGender gender) {
        this.gender = gender;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

}
