package bst.bobsoolting.like.command.application.controller;

import bst.bobsoolting.like.command.application.controller.docs.PostLikeCommandControllerDocs;
import bst.bobsoolting.like.command.application.service.PostLikeCommandService;
import bst.bobsoolting.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostLikeCommandController implements PostLikeCommandControllerDocs {

    private final PostLikeCommandService postLikeCommandService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        log.info("좋아요 요청 - postId: {}, kakaoId: {}", postId, kakaoId);
        try {
            postLikeCommandService.likePost(postId, kakaoId);
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
