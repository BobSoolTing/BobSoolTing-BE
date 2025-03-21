package bst.bobsoolting.like.command.application.mapper;

import bst.bobsoolting.like.command.application.dto.PostLikeDTO;
import bst.bobsoolting.like.command.domain.vo.response.ResponsePostLikeVO;
import org.springframework.stereotype.Component;

@Component
public class PostLikeConverter {

    public ResponsePostLikeVO fromDTOToVO(PostLikeDTO postLikeDTO) {
        return ResponsePostLikeVO.builder()
                .likeId(postLikeDTO.getLikeId())
                .createdAt(postLikeDTO.getCreatedAt())
                .postId(postLikeDTO.getPostId())
                .memberId(postLikeDTO.getMemberId())
                .build();
    }
}
