package bst.bobsoolting.post.command.application.controller;

import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.post.command.application.controller.docs.PostCommandControllerDocs;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.application.service.PostCommandService;
import bst.bobsoolting.post.command.domain.vo.request.RequestCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.request.RequestUpdatePostVO;
import bst.bobsoolting.post.command.domain.vo.response.ResponseCreatePostVO;
import bst.bobsoolting.post.command.domain.vo.response.ResponseUpdatePostVO;
import bst.bobsoolting.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostCommandController implements PostCommandControllerDocs {

    private final PostCommandService postCommandService;
    private final PostConverter postConverter;
    private final MemberQueryService memberQueryService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody RequestCreatePostVO request) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Unauthorized access"));

        PostDTO created = postCommandService.createPost(request, memberId);
        ResponseCreatePostVO response = postConverter.fromDTOToCreateVO(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long postId, @RequestBody RequestUpdatePostVO request) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Unauthorized access"));

        PostDTO updated = postCommandService.updatePost(memberId, postId, request);
        ResponseUpdatePostVO response = postConverter.fromDTOToUpdateVO(updated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}/recruit-status")
    public ResponseEntity<Map<String, String>> updateRecruitmentStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("postId") Long postId) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Unauthorized access"));

        postCommandService.updateRecruitmentStatus(memberId, postId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "모집 상태가 변경되었습니다.");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Map<String, String>> softDeletePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("postId") Long postId) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Unauthorized access"));
        postCommandService.deletePost(memberId, postId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "해당 게시글이 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }
}