package bst.bobsoolting.member.command.application.controller;

import bst.bobsoolting.common.exception.CommonException;
import bst.bobsoolting.member.command.application.controller.docs.MemberCommandControllerDocs;
import bst.bobsoolting.member.command.application.dto.MemberDTO;
import bst.bobsoolting.member.command.application.mapper.MemberConverter;
import bst.bobsoolting.member.command.application.service.MemberCommandService;
import bst.bobsoolting.member.command.domain.vo.request.RequestAdditionalRegisterVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestKakaoAuthVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestRefreshTokenVO;
import bst.bobsoolting.member.command.domain.vo.request.RequestUpdateProfileVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseCreateMemberVO;
import bst.bobsoolting.member.command.domain.vo.response.ResponseProfileVO;
import bst.bobsoolting.security.JwtTokenProvider;
import bst.bobsoolting.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberCommandController implements MemberCommandControllerDocs {

    private final MemberCommandService memberCommandService;
    private final MemberConverter memberConverter;
    private final SecurityUtil securityUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/kakao")
    public ResponseEntity<Map<String, String>> kakaoLogin(@RequestBody RequestKakaoAuthVO request) {
        String code = request.getCode();
        if (code == null || code.isEmpty()) {
            log.error("인가 코드가 전달되지 않았습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "인가 코드가 없습니다."));
        }

        log.info("카카오 로그인 요청. 인가 코드: {}", code);
        try {
            String kakaoAccessToken = memberCommandService.getKakaoAccessToken(code);
            MemberDTO member = memberCommandService.getKakaoUserInfo(kakaoAccessToken);

            String serverJwtToken = jwtTokenProvider.generateAccessToken(member.getKakaoId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(member.getKakaoId());

            memberCommandService.storeRefreshToken(member.getKakaoId(), refreshToken);

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("access_token", serverJwtToken);
            responseMap.put("refresh_token", refreshToken);

            return ResponseEntity.ok(responseMap);
        } catch (CommonException e) {
            log.error("카카오 로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "카카오 로그인 처리 중 오류 발생"));
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RequestRefreshTokenVO request) {
        String refreshToken = request.getRefresh_token();

        if (refreshToken == null || refreshToken.isEmpty() || jwtTokenProvider.isTokenExpired(refreshToken)) {
            log.warn("유효하지 않은 Refresh Token 요청 감지");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token입니다.");
        }

        Claims claims = jwtTokenProvider.parseToken(refreshToken);
        String kakaoId = claims.getSubject();

        String storedRefreshToken = memberCommandService.getRefreshToken(kakaoId);

        if (storedRefreshToken == null) {
            log.warn("Redis에서 Refresh Token Not Found - Kakao ID: {}", kakaoId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 만료되었거나 존재하지 않습니다.");
        }
        if (!refreshToken.equals(storedRefreshToken)) {
            log.warn("Refresh Token 불일치 - Kakao ID: {}", kakaoId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 일치하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(kakaoId);
        Map<String, String> response = new HashMap<>();
        response.put("access_token", newAccessToken);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/complete")
    public ResponseEntity<ResponseCreateMemberVO> completeRegistration(@RequestBody RequestAdditionalRegisterVO info, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        log.info("추가 회원가입 정보 수신: kakaoId={}, 추가 정보={}", kakaoId, info);
        try {
            MemberDTO updatedMember = memberCommandService.updateMemberAdditionalInfo(kakaoId, info);
            ResponseCreateMemberVO response = memberConverter.fromEntityToCreateVO(updatedMember);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("추가 회원가입 정보 업데이트 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/profile")
    public ResponseEntity<ResponseProfileVO> updateProfile(@RequestBody RequestUpdateProfileVO updateInfo, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String kakaoId = securityUtil.getKakaoIdFromToken(token.replace("Bearer ", ""));
        log.info("프로필 수정 요청: {} -> {}", kakaoId, updateInfo);
        MemberDTO updatedMember = memberCommandService.updateMemberProfile(kakaoId, updateInfo);
        ResponseProfileVO responseProfileVO = memberConverter.fromEntityToProfileVO(updatedMember);
        return ResponseEntity.ok(responseProfileVO);
    }
}
