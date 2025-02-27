package bst.bobsoolting.member.query.service;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.common.exception.ErrorCode;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.query.repository.MemberMapper; // 예시: MyBatis Mapper
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;

    @Override
    public void processLoginSuccess(OAuth2User user) {
        Long kakaoIdLong = user.getAttribute("id");
        String kakaoId = String.valueOf(kakaoIdLong);
        log.info("로그인 성공 처리 시작. kakaoId: {}", kakaoId);

        Member existingMember = memberMapper.findByKakaoId(kakaoId);
        if (existingMember == null) {
            log.info("신규 회원입니다. 추가 정보 입력 필요합니다. kakaoId: {}", kakaoId);
            throw new CommonException(ErrorCode.NEW_MEMBER_REGISTRATION_REQUIRED);
        } else {
            log.info("기존 회원 로그인 성공. kakaoId: {}", kakaoId);
        }
    }
}
