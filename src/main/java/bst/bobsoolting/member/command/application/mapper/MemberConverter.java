package bst.bobsoolting.member.command.application.mapper;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberDTO fromEntityToDTO(Member newMember) {
        return MemberDTO.builder()
                .memberId(newMember.getMemberId())
                .kakaoId(newMember.getKakaoId())
                .nickname(newMember.getNickname())
                .phone(newMember.getPhone())
                .profileImage(newMember.getProfileImage())
                .gender(newMember.getGender())
                .birth(newMember.getBirth())
                .university(newMember.getUniversity())
                .department(newMember.getDepartment())
                .studentNumber(newMember.getStudentNumber())
                .rating(newMember.getRating())
                .memberRole(newMember.getMemberRole())
                .build();
    }

    public ResponseCreateMemberVO fromEntityToCreateVO(MemberDTO member) {
        return ResponseCreateMemberVO.builder()
                .memberId(member.getMemberId())
                .kakaoId(member.getKakaoId())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .profileImage(member.getProfileImage())
                .gender(member.getGender())
                .birth(member.getBirth())
                .university(member.getUniversity())
                .department(member.getDepartment())
                .studentNumber(member.getStudentNumber())
                .rating(member.getRating())
                .memberRole(member.getMemberRole())
                .build();
    }

    public ResponseProfileVO fromDTOToProfileVO(MemberDTO dto) {
        return ResponseProfileVO.builder()
                .nickname(dto.getNickname())
                .rating(dto.getRating())
                .profileImage(dto.getProfileImage())
                .build();
    }

    public ResponseDetailVO fromDTOToDetailVO(MemberDTO dto) {
        return ResponseDetailVO.builder()
                .nickname(dto.getNickname())
                .profileImage(dto.getProfileImage())
                .university(dto.getUniversity())
                .department(dto.getDepartment())
                .studentNumber(dto.getStudentNumber())
                .gender(dto.getGender())
                .birth(dto.getBirth())
                .rating(dto.getRating())
                .build();
    }

    public ResponseProfileVO fromEntityToProfileVO(MemberDTO updatedMember) {
        return ResponseProfileVO.builder()
                .nickname(updatedMember.getNickname())
                .rating(updatedMember.getRating())
                .profileImage(updatedMember.getProfileImage())
                .build();
    }
}
