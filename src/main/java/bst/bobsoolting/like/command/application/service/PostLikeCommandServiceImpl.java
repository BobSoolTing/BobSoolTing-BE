package bst.bobsoolting.like.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.like.command.domain.aggregate.entity.PostLike;
import bst.bobsoolting.like.command.domain.repository.PostLikeRepository;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeCommandServiceImpl implements PostLikeCommandService {

    private final PostLikeRepository postLikeRepository;
    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public void likePost(Long postId, OAuth2User user) {
        log.info("좋아요 처리 시작 - postId: {}", postId);
        try {
            Long kakaoIdLong = user.getAttribute("id");
            String kakaoId = String.valueOf(kakaoIdLong);

            Member member = memberMapper.findByKakaoId(kakaoId);
            if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
            String memberId = member.getMemberId();

            Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndMemberId(postId, memberId);
            if (existingLike.isPresent()) throw new CommonException(ErrorCode.ALREADY_LIKED);

            PostLike postLike = PostLike.builder()
                    .postId(postId)
                    .memberId(memberId)
                    .build();

            postLikeRepository.save(postLike);
            log.info("좋아요 등록 완료 - postId: {}, memberId: {}", postId, memberId);

        } catch (CommonException e) {
            log.error("좋아요 처리 오류: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
