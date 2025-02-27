package bst.bobsoolting.member.command.application.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
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

    private String generateMemberId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(20);

        return datePart + uuidPart;
    }
}
