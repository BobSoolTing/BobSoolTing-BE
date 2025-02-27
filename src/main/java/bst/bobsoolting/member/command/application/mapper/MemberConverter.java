package bst.bobsoolting.member.command.application.mapper;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberDTO fromAdditionalVOToEntity(RequestAdditionalRegisterVO info) {
        return MemberDTO.builder()
                .kakaoId(info.getKakaoId())
                .nickname(info.getNickname())
                .phone(info.getPhone())
                .gender(info.getGender())
                .birth(info.getBirth())
                .university(info.getUniversity())
                .department(info.getDepartment())
                .studentNumber(info.getStudentNumber())
                .rating(0.0f) // 초기 rating(당근 온도 개념)
                .memberRole(MemberRole.ROLE_USER)
                .build();
    }

    public Member fromDTOToEntity(MemberDTO newMemberDTO, String memberId) {
        return Member.builder()
                .memberId(memberId)
                .kakaoId(newMemberDTO.getKakaoId())
                .nickname(newMemberDTO.getNickname())
                .phone(newMemberDTO.getPhone())
                .gender(newMemberDTO.getGender())
                .birth(newMemberDTO.getBirth())
                .university(newMemberDTO.getUniversity())
                .department(newMemberDTO.getDepartment())
                .studentNumber(newMemberDTO.getStudentNumber())
                .rating(newMemberDTO.getRating())
                .memberRole(newMemberDTO.getMemberRole())
                .build();
    }
}
