package bst.bobsoolting.post.query.service;

import bst.bobsoolting.post.command.application.mapper.PostConverter;
import bst.bobsoolting.post.command.application.dto.PostDTO;
import bst.bobsoolting.post.command.domain.aggregate.entity.Post;
import bst.bobsoolting.post.query.repository.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostMapper postMapper;
    private final PostConverter postConverter;

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postMapper.findByPostId(postId);
        return postConverter.toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postMapper.findAll();
        return posts.stream().map(postConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByCategory(String category) {
        List<Post> posts = postMapper.findByCategory(category);
        return posts.stream().map(postConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post> posts = postMapper.searchByKeyword(keyword);
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByMemberId(String memberId) {
        List<Post> posts = postMapper.findByMemberId(memberId);
        return posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());
    }
}
