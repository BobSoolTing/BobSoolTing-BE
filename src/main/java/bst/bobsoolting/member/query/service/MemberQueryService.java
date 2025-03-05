package bst.bobsoolting.member.query.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;

public interface MemberQueryService {
    void processLoginSuccess(String kakaoId);

    MemberDTO getMemberProfile(String kakaoId);

    MemberDTO getMemberDetail(String kakaoId);
}
