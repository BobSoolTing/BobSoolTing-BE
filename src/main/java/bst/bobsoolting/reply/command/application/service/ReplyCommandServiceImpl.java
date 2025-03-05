package bst.bobsoolting.reply.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import bst.bobsoolting.reply.command.application.dto.ReplyDTO;
import bst.bobsoolting.reply.command.application.mapper.ReplyConverter;
import bst.bobsoolting.reply.command.domain.aggregate.entity.Reply;
import bst.bobsoolting.reply.command.domain.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyCommandServiceImpl implements ReplyCommandService {

    private final ReplyRepository replyRepository;
    private final ReplyConverter replyConverter;
    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public ReplyDTO createReply(ReplyDTO replyDTO, String kakaoId) {
        log.info("대댓글 등록 - kakaoId: {}, 데이터: {}", kakaoId, replyDTO);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) {
            log.error("대댓글 등록 실패 - 해당 kakaoId의 회원이 없음: {}", kakaoId);
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }
        replyDTO.setMemberId(member.getMemberId());

        Reply reply = replyConverter.fromDTOToEntity(replyDTO);
        reply.create(reply);

        Reply savedReply = replyRepository.save(reply);
        log.info("대댓글 등록 성공 - replyId: {}", savedReply.getReplyId());

        return replyConverter.fromEntityToDTO(savedReply);
    }

    @Transactional
    @Override
    public ReplyDTO updateReply(ReplyDTO replyDTO, String kakaoId) {
        log.info("대댓글 수정 - replyId: {}, kakaoId: {}", replyDTO.getReplyId(), kakaoId);

        Reply existingReply = replyRepository.findById(replyDTO.getReplyId())
                .orElseThrow(() -> {
                    log.error("대댓글 수정 실패 - 해당 replyId 없음: {}", replyDTO.getReplyId());
                    return new CommonException(ErrorCode.NOT_FOUND_REPLY);
                });

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null || !existingReply.getMemberId().equals(member.getMemberId())) {
            log.error("대댓글 수정 실패 - 접근 권한 없음: kakaoId={}, replyId={}", kakaoId, replyDTO.getReplyId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingReply.updateContent(replyDTO.getReplyContent());
        replyRepository.save(existingReply);
        log.info("대댓글 수정 완료 - replyId: {}", existingReply.getReplyId());

        return replyConverter.fromEntityToDTO(existingReply);
    }

    @Transactional
    @Override
    public ReplyDTO deleteReply(Long replyId, String kakaoId) {
        log.info("대댓글 삭제 - replyId: {}, kakaoId: {}", replyId, kakaoId);

        Reply existingReply = replyRepository.findById(replyId)
                .orElseThrow(() -> {
                    log.error("대댓글 삭제 실패 - 해당 replyId 없음: {}", replyId);
                    return new CommonException(ErrorCode.NOT_FOUND_REPLY);
                });

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null || !existingReply.getMemberId().equals(member.getMemberId())) {
            log.error("대댓글 삭제 실패 - 접근 권한 없음: kakaoId={}, replyId={}", kakaoId, replyId);
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingReply.updateStatus(false);
        replyRepository.save(existingReply);
        log.info("대댓글 삭제 완료 - replyId: {}", replyId);

        return replyConverter.fromEntityToDTO(existingReply);
    }
}
