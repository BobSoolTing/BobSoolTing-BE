package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.MemberGender;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.query.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public void createBasicMember(String kakaoId) {
        log.info("신규 회원 기본 정보 저장 진행. kakaoId={}", kakaoId);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        if (existingMember != null) {
            log.warn("이미 존재하는 회원입니다. kakaoId={}", kakaoId);
            throw new CommonException(ErrorCode.ALREADY_EXISTS);
        }
        Member newMember = createMember(kakaoId);
        memberRepository.save(newMember);
        log.info("신규 회원 기본 정보 저장 완료. kakaoId={}", kakaoId);
    }

    @Override
    @Transactional
    public MemberDTO updateMemberAdditionalInfo(String kakaoId, RequestAdditionalRegisterVO info) {
        log.info("추가 회원가입 정보 업데이트 진행. kakaoId={}, 추가 정보={}", kakaoId, info);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        if (existingMember == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

        updateMemberAdditionalInfo(info, existingMember);

        memberRepository.save(existingMember);
        log.info("추가 정보 업데이트 완료. kakaoId={}", kakaoId);

        return memberConverter.fromEntityToDTO(existingMember);
    }

    @Override
    @Transactional
    public MemberDTO updateMemberProfile(String kakaoId, RequestUpdateProfileVO updateInfo) {
        log.info("프로필 수정 진행. kakaoId={}, 변경 정보={}", kakaoId, updateInfo);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

        boolean isUpdated = false;
        if (updateInfo.getProfileImage() != null) {
            member.setProfileImage(updateInfo.getProfileImage());
            isUpdated = true;
        }
        if (updateInfo.getNickname() != null) {
            member.setNickname(updateInfo.getNickname());
            isUpdated = true;
        }
        if (updateInfo.getDepartment() != null) {
            member.setDepartment(updateInfo.getDepartment());
            isUpdated = true;
        }

        if (!isUpdated) return memberConverter.fromEntityToDTO(member);

        memberRepository.save(member);
        log.info("프로필 수정 완료. kakaoId={}, 수정된 정보={}", kakaoId, updateInfo);

        return memberConverter.fromEntityToDTO(member);
    }

    private Member createMember(String kakaoId) {
        Member newMember = Member.builder()
                .kakaoId(kakaoId)
                .nickname("신규 유저")
                .phone("")
                .profileImage("")
                .gender(MemberGender.DEFAULT)
                .birth(new Date())
                .university("미입력")
                .department("미입력")
                .studentNumber(0)
                .rating(0.0f)
                .memberRole(MemberRole.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return newMember;
    }


    private String generateMemberId() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + UUID.randomUUID().toString().replace("-", "").substring(20);
    }

    private void updateMemberAdditionalInfo(RequestAdditionalRegisterVO info, Member existingMember) {
        if (info.getNickname() != null) existingMember.setNickname(info.getNickname());
        if (info.getPhone() != null) existingMember.setPhone(info.getPhone());
        if (info.getProfileImage() != null) existingMember.setProfileImage(info.getProfileImage());
        if (info.getGender() != null) existingMember.setGender(info.getGender());
        if (info.getBirth() != null) existingMember.setBirth(info.getBirth());
        if (info.getUniversity() != null) existingMember.setUniversity(info.getUniversity());
        if (info.getDepartment() != null) existingMember.setDepartment(info.getDepartment());
        if (info.getStudentNumber() != null) existingMember.setStudentNumber(info.getStudentNumber());
    }
}
