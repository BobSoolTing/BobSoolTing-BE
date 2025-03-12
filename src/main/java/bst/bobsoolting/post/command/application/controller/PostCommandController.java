package bst.bobsoolting.post.command.application.controller;

import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.post.command.application.controller.docs.PostCommandControllerDocs;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.service.PostCommandService;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import bst.bobsoolting.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class PostCommandController implements PostCommandControllerDocs {

    private final PostCommandService postCommandService;
    private final MemberQueryService memberQueryService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody RequestCreatePostVO request) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        PostDTO created = postCommandService.createPost(request, memberId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long postId, @RequestBody RequestUpdatePostVO request) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        PostDTO updated = postCommandService.updatePost(memberId, postId, request);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> softDeletePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("postId") Long postId) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        postCommandService.deletePost(memberId, postId);

        return ResponseEntity.noContent().build();
    }
}