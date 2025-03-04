package bst.bobsoolting.like.command.application.controller;

import bst.bobsoolting.like.command.application.service.PostLikeCommandService;
import bst.bobsoolting.common.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/like")
@Slf4j
@RequiredArgsConstructor
public class PostLikeCommandController {

    private final PostLikeCommandService postLikeCommandService;

    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요 기능입니다.")
    @PostMapping("/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal OAuth2User user) {
        log.info("좋아요 요청 - postId: {}, userId: {}", postId, user.getAttribute("id"));
        try {
            postLikeCommandService.likePost(postId, user);
            return ResponseEntity.ok("좋아요가 성공적으로 등록되었습니다.");
        } catch (CommonException e) {
            log.error("좋아요 처리 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 처리 중 오류 발생");
        }
    }
}
