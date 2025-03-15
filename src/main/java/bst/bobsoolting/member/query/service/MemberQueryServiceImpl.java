package bst.bobsoolting.member.query.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.MemberRole;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.repository.MemberRepository;
import bst.bobsoolting.member.query.repository.MemberMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;

    @Override
    public MemberDTO getMemberProfile(String kakaoId) {
        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        return memberConverter.fromEntityToDTO(member);
    }

    @Override
    public MemberDTO getMemberDetail(String kakaoId) {
        return getMemberProfile(kakaoId);
    }

    @Override
    public String getMemberIdByKakaoId(String kakaoId) {
        Member member = memberMapper.findByKakaoId(kakaoId);
        return (member != null) ? member.getMemberId() : null;
    }
}
