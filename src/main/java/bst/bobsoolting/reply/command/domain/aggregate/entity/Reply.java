package bst.bobsoolting.reply.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "reply_content", nullable = false)
    private String replyContent;

    @Column(name = "reply_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean replyStatus = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void create(Reply reply) {
        reply.createdAt = LocalDateTime.now();
    }

    public void updateContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public void updateStatus(boolean status) {
        this.replyStatus = status;
    }
}
