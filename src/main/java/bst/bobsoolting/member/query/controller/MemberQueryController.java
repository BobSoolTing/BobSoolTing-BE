package bst.bobsoolting.member.query.controller;

import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import bst.bobsoolting.member.command.domain.vo.response.ResponseDetailVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import bst.bobsoolting.member.query.controller.docs.MemberQueryControllerDocs;
import bst.bobsoolting.member.query.repository.MemberMapper;
import bst.bobsoolting.member.query.service.MemberQueryService;
import bst.bobsoolting.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberQueryController implements MemberQueryControllerDocs {

    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;
    private final MemberMapper memberMapper;

    @GetMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            log.error("❌ OAuth2User 정보가 없습니다. 인증 실패 가능성 있음.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2 인증 정보가 없습니다.");
        }

        log.info("✅ OAuth2User 정보: {}", user.getAttributes());

        try {
            Long kakaoIdLong = user.getAttribute("id");
            String kakaoId = String.valueOf(kakaoIdLong);
            log.info("카카오 로그인 성공: {}", kakaoId);

            Member existingMember = memberMapper.findByKakaoId(kakaoId);
            boolean isNewMember = (existingMember == null);

            if (isNewMember) return ResponseEntity.ok("신규 회원 - 추가 정보 입력 필요");
            else return ResponseEntity.ok("기존 회원 로그인 성공");
        } catch (Exception e) {
            log.error("로그인 성공 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 성공 처리 중 오류 발생");
        }
    }

    @GetMapping("/loginFailure")
    public ResponseEntity<String> loginFailure() {
        log.info("카카오 로그인 실패");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    @GetMapping("/my-page")
    public ResponseEntity<ResponseProfileVO> getMyProfile(@AuthenticationPrincipal OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
        log.info("마이페이지 조회 요청: {}", kakaoId);
        try {
            MemberDTO memberDTO = memberQueryService.getMemberProfile(kakaoId);
            ResponseProfileVO profileVO = memberConverter.fromDTOToProfileVO(memberDTO);
            return ResponseEntity.ok(profileVO);
        } catch (CommonException e) {
            log.error("마이페이지 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDetailVO> getMyProfileDetails(@AuthenticationPrincipal OAuth2User user) {
        String kakaoId = String.valueOf(user.getAttribute("id"));
        log.info("프로필 상세 조회 요청: {}", kakaoId);
        try {
            MemberDTO memberDTO = memberQueryService.getMemberDetail(kakaoId);
            ResponseDetailVO detailVO = memberConverter.fromDTOToDetailVO(memberDTO);
            return ResponseEntity.ok(detailVO);
        } catch (CommonException e) {
            log.error("프로필 상세 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
