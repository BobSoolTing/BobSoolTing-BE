package bst.bobsoolting.like.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.like.command.application.dto.PostLikeDTO;
import bst.bobsoolting.like.command.domain.aggregate.entity.PostLike;
import bst.bobsoolting.like.command.domain.repository.PostLikeRepository;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeCommandServiceImpl implements PostLikeCommandService {

    private final PostLikeRepository postLikeRepository;
    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public PostLikeDTO likePost(Long postId, String kakaoId) {
        log.info("좋아요 처리 시작 - postId: {}", postId);
        try {
            Member member = memberMapper.findByKakaoId(kakaoId);
            if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
            String memberId = member.getMemberId();

            PostLike existingLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId);
            if (existingLike != null) throw new CommonException(ErrorCode.ALREADY_LIKED);

            PostLike postLike = like(postId, memberId);

            postLikeRepository.save(postLike);
            log.info("좋아요 등록 완료 - postId: {}, memberId: {}", postId, memberId);

        } catch (CommonException e) {
            log.error("좋아요 처리 오류: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private PostLike like(Long postId, String memberId) {
        PostLike postLike = PostLike.builder()
                .createdAt(LocalDateTime.now())
                .postId(postId)
                .memberId(memberId)
                .build();
        return postLike;
    }
}
