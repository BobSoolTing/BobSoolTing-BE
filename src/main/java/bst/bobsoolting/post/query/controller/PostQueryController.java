package bst.bobsoolting.post.query.controller;

import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.domain.vo.response.ResponsePostVO;
import bst.bobsoolting.post.query.controller.docs.PostQueryControllerDocs;
import bst.bobsoolting.post.query.service.PostQueryService;
import bst.bobsoolting.util.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class PostQueryController implements PostQueryControllerDocs {

    private final PostQueryService postQueryService;
    private final PostConverter postConverter;
    private final MemberQueryService memberQueryService;
    private final SecurityUtil securityUtil;

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostVO> getPostById(@PathVariable("postId") Long postId) {
        PostDTO dto = postQueryService.getPostById(postId);
        ResponsePostVO response = postConverter.toResponseVO(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageInfo<ResponsePostVO>> getAllPosts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        PageHelper.startPage(page, size);
        List<ResponsePostVO> posts = postQueryService.getAllPosts().stream()
                .map(postConverter::toResponseVO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageInfo<>(posts));
    }

    @GetMapping("/category")
    public ResponseEntity<PageInfo<ResponsePostVO>> getPostsByCategory(@RequestParam("category") String category, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        PageHelper.startPage(page, size);
        List<ResponsePostVO> posts = postQueryService.getPostsByCategory(category).stream()
                .map(postConverter::toResponseVO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageInfo<>(posts));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponsePostVO>> searchPosts(@RequestParam("keyword") String keyword) {
        List<ResponsePostVO> posts = postQueryService.searchPostsByKeyword(keyword).stream()
                .map(postConverter::toResponseVO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/member")
    public ResponseEntity<List<ResponsePostVO>> getMyPosts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        String memberId = memberQueryService.getMemberIdByKakaoId(kakaoId);
        if (memberId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        List<ResponsePostVO> posts = postQueryService.getPostsByMemberId(memberId).stream()
                .map(postConverter::toResponseVO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }
}
