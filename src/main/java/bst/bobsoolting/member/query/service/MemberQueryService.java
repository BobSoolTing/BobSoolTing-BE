package bst.bobsoolting.member.query.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface MemberQueryService {
    void processLoginSuccess(OAuth2User user);

    MemberDTO getMemberProfile(OAuth2User user);

    MemberDTO getMemberDetail(OAuth2User user);
}
