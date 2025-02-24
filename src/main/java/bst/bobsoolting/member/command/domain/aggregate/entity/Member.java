package bst.bobsoolting.member.command.domain.aggregate.entity;

import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
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
    private Long memberId;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberGender gender;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "university", nullable = false)
    private String university;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;

    @Column(name = "rating", nullable = false)
    private Float rating;

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
}
