package bst.bobsoolting.comment.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean commentStatus = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    public void create(Comment comment) {
        comment.createdAt = LocalDateTime.now();
    }

    public void updateContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void updateStatus(boolean status) {
        this.commentStatus = status;
    }
}
