package bst.bobsoolting.reply.command.application.service;

import bst.bobsoolting.reply.command.application.dto.ReplyDTO;

public interface ReplyCommandService {
    ReplyDTO createReply(ReplyDTO replyDTO, String kakaoId);
    ReplyDTO updateReply(ReplyDTO replyDTO, String kakaoId);
    ReplyDTO deleteReply(Long replyId, String kakaoId);
}
