package bst.bobsoolting.member.query.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;
    private final MemberConverter memberConverter;

    @Override
    public void processLoginSuccess(OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        log.info("로그인 성공 처리 시작. kakaoId: {}", kakaoId);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        if (existingMember == null) {
            log.info("신규 회원입니다. 추가 정보 입력 필요. kakaoId: {}", kakaoId);
            throw new CommonException(ErrorCode.NEW_MEMBER_REGISTRATION_REQUIRED);
        } else {
            log.info("기존 회원 로그인 성공. kakaoId: {}", kakaoId);
        }
    }

    @Override
    public MemberDTO getMemberProfile(OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
        log.info("마이페이지 프로필 조회 요청. kakaoId: {}", kakaoId);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) {
            log.error("해당 kakaoId의 회원 정보가 없음: {}", kakaoId);
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        return memberConverter.fromEntityToDTO(member);
    }

    @Override
    public MemberDTO getMemberDetail(OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
        log.info("프로필 상세 조회 요청. kakaoId: {}", kakaoId);

        Member member = memberMapper.findByKakaoId(kakaoId);
        if (member == null) {
            log.error("해당 kakaoId의 회원 정보가 없음: {}", kakaoId);
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        return memberConverter.fromEntityToDTO(member);
    }
}
