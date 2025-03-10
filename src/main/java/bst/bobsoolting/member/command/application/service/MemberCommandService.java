package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    MemberDTO createMember(MemberDTO newMember);

    MemberDTO updateMemberProfile(OAuth2User user, RequestUpdateProfileVO updateInfo);

    @Transactional
    String getKakaoAccessToken(String code);

    MemberDTO getKakaoUserInfo(String accessToken);

    @Transactional
    MemberDTO createOrUpdateMember(String kakaoId, String nickname);
}
