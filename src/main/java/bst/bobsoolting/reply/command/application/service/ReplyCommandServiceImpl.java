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
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    public ReplyDTO createReply(ReplyDTO replyDTO, OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        String memberId = member.getMemberId();
        replyDTO.setMemberId(memberId);

        Reply reply = replyConverter.fromDTOToEntity(replyDTO);
        log.info("저장할 대댓글 데이터: {}", reply);

        reply.create(reply);
        Reply savedReply = replyRepository.save(reply);

        return replyConverter.fromEntityToDTO(savedReply);
    }

    @Transactional
    @Override
    public ReplyDTO updateReply(ReplyDTO replyDTO, OAuth2User user) {
        Reply existingReply = replyRepository.findById(replyDTO.getReplyId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REPLY));

        if (!existingReply.getMemberId().equals(replyDTO.getMemberId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingReply.updateContent(replyDTO.getReplyContent());
        replyRepository.save(existingReply);

        return replyConverter.fromEntityToDTO(existingReply);
    }

    @Transactional
    @Override
    public ReplyDTO deleteReply(Long replyId, OAuth2User user) {
        Reply existingReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REPLY));

        existingReply.updateStatus(false);
        replyRepository.save(existingReply);

        return replyConverter.fromEntityToDTO(existingReply);
    }
}
