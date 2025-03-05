package bst.bobsoolting.post.query.service;

import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.Post;
import bst.bobsoolting.post.command.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findByPostId(postId);
        return PostConverter.toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post> posts = postRepository.searchByKeyword(keyword);
        return posts.stream()
                .map(PostConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> getPostsByCategory(String category) {
        // Repository에서 category를 기준으로 조회한 후 DTO로 변환
        List<Post> posts = postRepository.findByCategory(category);
        return posts.stream()
                .map(PostConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostDTO> getPostsByMemberId(String memberId) {
        List<Post> posts = postRepository.findByMemberId(memberId);
        return posts.stream()
                .map(PostConverter::toDTO)
                .collect(Collectors.toList());
    }
}
