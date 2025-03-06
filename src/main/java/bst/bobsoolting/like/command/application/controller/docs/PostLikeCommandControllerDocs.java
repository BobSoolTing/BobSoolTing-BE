package bst.bobsoolting.like.command.application.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "게시글 좋아요 API", description = "게시글 좋아요 관련 API 문서")
@RequestMapping("api/like")
public interface PostLikeCommandControllerDocs {

    @Operation(summary = "게시글 좋아요", description = "게시글에 좋아요를 추가하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/{postId}")
    ResponseEntity<String> likePost(@Parameter(description = "게시글 ID") @PathVariable Long postId);
}
