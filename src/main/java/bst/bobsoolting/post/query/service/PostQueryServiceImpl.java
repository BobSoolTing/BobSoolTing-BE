package bst.bobsoolting.post.query.service;

import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.Post;
import bst.bobsoolting.post.query.repository.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostMapper postmapper;
    private final PostConverter postConverter;

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postmapper.findByPostId(postId);
        return postConverter.toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postmapper.findAll();
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post> posts = postmapper.searchByKeyword(keyword);
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> getPostsByCategory(String category) {
        // postmapper에서 category를 기준으로 조회한 후 DTO로 변환
        List<Post> posts = postmapper.findByCategory(category);
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> getPostsByMemberId(String memberId) {
        List<Post> posts = postmapper.findByMemberId(memberId);
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }
}
