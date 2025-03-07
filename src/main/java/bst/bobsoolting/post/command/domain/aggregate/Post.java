package bst.bobsoolting.post.command.domain.aggregate;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "post")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Post {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;                  // PK (AUTO_INCREMENT)
	
	@Enumerated(EnumType.STRING)
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

}
