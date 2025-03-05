package bst.bobsoolting.post.command.domain.repository;

import bst.bobsoolting.post.command.domain.aggregate.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PostRepository {

	// 게시글 등록
    void insertPost(Post post);

    // 게시글 수정
    void updatePost(Post post);

    // 게시글 삭제 (소프트 딜리트: post_status를 false로 업데이트)
    void softDeletePost(@Param("postId") Long postId);

    //검색메소드
    
    // 게시글 ID로 단건 조회
    Post findByPostId(@Param("postId") Long postId);

    // 전체 게시글 조회 (필요 시)
    List<Post> findAll();
    
    //1.키워드
	List<Post> searchByKeyword(String keyword);
	//2.카테고리
	List<Post> findByCategory(@Param("category") String category);
	//3. 작성자id 조회
	List<Post> findByMemberId(@Param("memberId") String memberId);
	
}