package bst.bobsoolting.post.command.domain.aggregate;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
//포스트 임
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Post {

    private Long postId;                  // PK (AUTO_INCREMENT)
    private Category category;            // Enum (예: STUDY, MEETUP 등)
    private String title;
    private String content;
    private String images;                // JSON 컬럼
    private Integer maxParticipants;
    private String participants;          // JSON 컬럼
    private String recruitmentStatus;
    private LocalDate date;
    private String location;
    private Boolean postStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberId;              // 외래키

    /**
     * 게시글 수정 비즈니스 로직
     */
    public void update(String title,
                       String content,
                       String images,
                       Integer maxParticipants,
                       String recruitmentStatus,
                       LocalDate date,
                       String location) {
        this.title = title;
        this.content = content;
        this.images = images;
        this.maxParticipants = maxParticipants;
        this.recruitmentStatus = recruitmentStatus;
        this.date = date;
        this.location = location;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 게시글 삭제 (소프트 딜리트)
     */
    public void delete() {
        this.postStatus = false;
        this.updatedAt = LocalDateTime.now();
    }
}
