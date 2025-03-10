package bst.bobsoolting.post.query.service;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import java.util.List;

public interface PostQueryService {
    //글번호로 검색
	PostDTO getPostById(Long postId);
    
	//전체 글 검색
    List<PostDTO> getAllPosts();
    
    //키워드 (제목/내용) 검색
    List<PostDTO> searchPostsByKeyword(String keyword);
    
    // Category 검색
    List<PostDTO> getPostsByCategory(String category);
    
    // 작성자 id 검색
    List<PostDTO> getPostsByMemberId(String memberId);
}
