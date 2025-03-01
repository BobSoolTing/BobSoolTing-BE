package bst.bobsoolting.reply.command.application.service;

import bst.bobsoolting.reply.command.application.dto.ReplyDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ReplyCommandService {
    ReplyDTO createReply(ReplyDTO replyDTO, OAuth2User user);
    ReplyDTO updateReply(ReplyDTO replyDTO, OAuth2User user);
    ReplyDTO deleteReply(Long replyId, OAuth2User user);
}
