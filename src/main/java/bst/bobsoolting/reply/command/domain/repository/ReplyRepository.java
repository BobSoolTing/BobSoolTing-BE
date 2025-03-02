package bst.bobsoolting.reply.command.domain.repository;

import bst.bobsoolting.reply.command.domain.aggregate.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
