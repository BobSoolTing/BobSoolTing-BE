package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.member.command.application.dto.MemberDTO;

public interface MemberCommandService {
    void createMember(MemberDTO newMember);
}
