package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;

public interface MemberCommandService {
    MemberDTO createMember(MemberDTO newMember);

    MemberDTO updateMemberProfile(String kakaoId, RequestUpdateProfileVO updateInfo);
}
