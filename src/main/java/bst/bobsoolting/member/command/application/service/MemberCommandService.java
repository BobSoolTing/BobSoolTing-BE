package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;

public interface MemberCommandService {
    MemberDTO updateMemberProfile(String kakaoId, RequestUpdateProfileVO updateInfo);

    MemberDTO updateMemberAdditionalInfo(String kakaoId, RequestAdditionalRegisterVO info);

    void createBasicMember(String kakaoId);
}
