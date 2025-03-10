package bst.bobsoolting.post.query.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import bst.bobsoolting.post.command.domain.aggregate.Post;

@Mapper
public interface PostMapper {

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
