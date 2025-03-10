package bst.bobsoolting.comment.command.application.service;

import bst.bobsoolting.comment.command.application.dto.CommentDTO;
import bst.bobsoolting.comment.command.application.mapper.CommentConverter;
import bst.bobsoolting.comment.command.domain.aggregate.entity.Comment;
import bst.bobsoolting.comment.command.domain.repository.CommentRepository;
import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, String kakaoId) {
        Member member = memberMapper.findByKakaoId(kakaoId);
        String memberId = member.getMemberId();
        commentDTO.setMemberId(memberId);

        Comment comment = commentConverter.fromDTOToEntity(commentDTO);
        log.info("저장할 댓글 데이터: {}", comment);

        comment.create(comment);
        Comment savedComment = commentRepository.save(comment);

        return commentConverter.fromEntityToDTO(savedComment);
    }

    @Transactional
    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, String kakaoId) {
        Member member = memberMapper.findByKakaoId(kakaoId);
        String memberId = member.getMemberId();
        commentDTO.setMemberId(memberId);

        Comment existingComment = commentRepository.findById(commentDTO.getCommentId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("기존 Comment 데이터: {}", existingComment);

        if (!existingComment.getMemberId().equals(memberId)) {
            log.error("수정 권한 없음: 요청한 사용자 ID: {}, 댓글 작성자 ID: {}", memberId, existingComment.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        updateCommentContent(commentDTO, existingComment);
        log.info("수정 후 Comment 데이터: {}", existingComment);

        commentRepository.save(existingComment);

        return commentConverter.fromEntityToDTO(existingComment);
    }

    @Transactional
    @Override
    public CommentDTO deleteComment(Long commentId, String kakaoId) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        log.info("삭제 요청된 Comment 데이터: {}", existingComment);

        Member member = memberMapper.findByKakaoId(kakaoId);
        String memberId = member.getMemberId();

        if (!existingComment.getMemberId().equals(memberId)) {
            log.error("삭제 권한 없음: 요청한 사용자 ID: {}, 댓글 작성자 ID: {}", memberId, existingComment.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingComment.updateStatus(false);
        log.info("상태 변경 후 Comment : {}", existingComment);

        commentRepository.save(existingComment);

        return commentConverter.fromEntityToDTO(existingComment);
    }

    private void updateCommentContent(CommentDTO commentDTO, Comment existingComment) {
        if (commentDTO.getCommentContent() != null) {
            existingComment.updateContent(commentDTO.getCommentContent());
        }
    }
}
