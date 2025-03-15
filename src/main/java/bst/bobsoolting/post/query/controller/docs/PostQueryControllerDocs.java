package bst.bobsoolting.post.query.controller.docs;

import bst.bobsoolting.post.command.application.dto.PostDTO;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 조회 API", description = "게시글 조회 및 검색 관련 API")
@RequestMapping("/api/post")
public interface PostQueryControllerDocs {

    @Operation(summary = "게시글 단건 조회", description = "특정 ID를 가진 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{postId}")
    ResponseEntity<PostDTO> getPostById(
            @Parameter(description = "게시글 ID", required = true) @PathVariable("postId") Long postId
    );

    @Operation(summary = "전체 게시글 조회 (페이지네이션)", description = "페이지네이션 적용된 모든 게시글을 조회합니다.")
    @GetMapping
    ResponseEntity<PageInfo<PostDTO>> getAllPosts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "카테고리별 게시글 조회 (페이지네이션)", description = "특정 카테고리에 속한 게시글을 페이지네이션하여 조회합니다.")
    @GetMapping("/category")
    ResponseEntity<PageInfo<PostDTO>> getPostsByCategory(@Parameter(description = "카테고리명", required = true, example = "FOOD") @RequestParam("category") String category, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "게시글 검색", description = "키워드를 이용해 게시글을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/search")
    ResponseEntity<List<PostDTO>> searchPosts(
            @Parameter(description = "검색 키워드", required = true, example = "스터디") @RequestParam("keyword") String keyword
    );

    @Operation(summary = "내가 작성한 게시글 조회", description = "JWT에서 로그인한 사용자의 memberId를 가져와 해당 사용자의 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostDTO.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/member")
    ResponseEntity<List<PostDTO>> getMyPosts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    );
}