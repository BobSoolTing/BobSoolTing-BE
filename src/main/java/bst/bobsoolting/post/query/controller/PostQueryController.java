package bst.bobsoolting.post.query.controller;

import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.query.controller.docs.PostQueryControllerDocs;
import bst.bobsoolting.post.query.service.PostQueryService;
import bst.bobsoolting.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class PostQueryController implements PostQueryControllerDocs {

    private final PostQueryService postQueryService;
    private final MemberQueryService memberQueryService;
    private final SecurityUtil securityUtil;

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

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam("keyword") String keyword) {
        List<PostDTO> posts = postQueryService.searchPostsByKeyword(keyword);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/category")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@RequestParam("category") String category) {
        List<PostDTO> posts = postQueryService.getPostsByCategory(category);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/member")
    public ResponseEntity<List<PostDTO>> getMyPosts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        List<PostDTO> posts = postQueryService.getPostsByMemberId(memberId);
        return ResponseEntity.ok(posts);
    }
}

