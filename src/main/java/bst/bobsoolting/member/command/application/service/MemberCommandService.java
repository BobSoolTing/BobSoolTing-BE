package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import org.springframework.transaction.annotation.Transactional;


public interface MemberCommandService {
    MemberDTO updateMemberProfile(String kakaoId, RequestUpdateProfileVO updateInfo);

    @Transactional
    String getKakaoAccessToken(String code);

    MemberDTO getKakaoUserInfo(String accessToken);

    @Transactional
    MemberDTO createOrUpdateMember(String kakaoId, String nickname);

    MemberDTO updateMemberAdditionalInfo(String kakaoId, RequestAdditionalRegisterVO info);
}
