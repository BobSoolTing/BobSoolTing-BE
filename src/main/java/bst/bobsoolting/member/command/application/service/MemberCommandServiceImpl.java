package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.query.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;

    @Override
    public MemberDTO createMember(MemberDTO newMemberDTO) {
        try {
            String memberId = generateMemberId();
            log.info("생성된 memberId: {}", memberId);

            Member newMember = memberConverter.fromDTOToEntity(newMemberDTO, memberId);

            memberRepository.save(newMember);
            log.info("회원가입 완료: kakaoId={}, memberId={}", newMember.getKakaoId(), newMember.getMemberId());
            return memberConverter.fromEntityToDTO(newMember);
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage(), e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public MemberDTO updateMemberProfile(OAuth2User user, RequestUpdateProfileVO updateInfo) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
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

        if (!isUpdated) {
            log.info("변경할 항목이 없음. kakaoId={}", kakaoId);
            return memberConverter.fromEntityToDTO(member);
        }

        memberRepository.save(member);
        log.info("프로필 수정 완료. kakaoId={}, 수정된 정보={}", kakaoId, updateInfo);

        return memberConverter.fromEntityToDTO(member);
    }

    private String generateMemberId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(20);
        return datePart + uuidPart;
    }
}
