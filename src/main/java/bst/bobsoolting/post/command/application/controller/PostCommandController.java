package bst.bobsoolting.post.command.application.controller;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.service.PostCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/command")
@RequiredArgsConstructor
public class PostCommandController {

    private final PostCommandService postCommandService;

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO created = postCommandService.createPost(postDTO);
        return ResponseEntity.ok(created);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId,
                                              @RequestBody PostDTO postDTO) {
        postDTO.setPostId(postId);
        PostDTO updated = postCommandService.updatePost(postDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * 게시글 삭제 (소프트 딜리트)
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> softDeletePost(@PathVariable("postId") Long postId) {
        postCommandService.softDeletePost(postId);
        return ResponseEntity.noContent().build();
    }

}

