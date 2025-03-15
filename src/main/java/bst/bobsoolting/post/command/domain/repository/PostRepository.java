package bst.bobsoolting.post.command.domain.repository;

import bst.bobsoolting.post.command.domain.aggregate.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}