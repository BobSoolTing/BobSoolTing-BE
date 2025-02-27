package bst.bobsoolting.member.query.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface MemberQueryService {
    void processLoginSuccess(OAuth2User user);
}
