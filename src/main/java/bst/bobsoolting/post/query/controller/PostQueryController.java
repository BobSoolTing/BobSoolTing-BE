package bst.bobsoolting.post.query.controller;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.query.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/post/query")
@RequiredArgsConstructor
public class PostQueryController {

    private final PostQueryService postQueryService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Long postId) {
        PostDTO dto = postQueryService.getPostById(postId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postQueryService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    
    // 새로운 검색 엔드포인트: 키워드를 쿼리 파라미터로 전달 (예: ?keyword=버거킹)
    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam("keyword") String keyword) {
        List<PostDTO> posts = postQueryService.searchPostsByKeyword(keyword);
        return ResponseEntity.ok(posts);
    }
    
    // Category별 조회 엔드포인트 (예: ?category=BOB)
    @GetMapping("/category")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@RequestParam("category") String category) {
        List<PostDTO> posts = postQueryService.getPostsByCategory(category);
        return ResponseEntity.ok(posts);
    }
    
 // 작성자(memberId)별 조회 엔드포인트 (예: ?memberId=test-user)
    @GetMapping("/member")
    public ResponseEntity<List<PostDTO>> getPostsByMemberId(@RequestParam("memberId") String memberId) {
        List<PostDTO> posts = postQueryService.getPostsByMemberId(memberId);
        return ResponseEntity.ok(posts);
    }
}
