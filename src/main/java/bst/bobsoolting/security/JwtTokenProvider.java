package bst.bobsoolting.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.access-token-validity}") long accessTokenValidity,
                            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) throw new IllegalArgumentException("JWT Secret Key 길이가 32바이트 이상이어야 합니다.");
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityInMilliseconds = accessTokenValidity;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidity;
    }

    public String generateAccessToken(String kakaoId) {
        return generateJwtToken(kakaoId, accessTokenValidityInMilliseconds);
    }

    public String generateRefreshToken(String kakaoId) {
        return generateJwtToken(kakaoId, refreshTokenValidityInMilliseconds);
    }

    private String generateJwtToken(String kakaoId, long validity) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setSubject(kakaoId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public Claims parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("✅ JWT 파싱 성공 - Claims: {}", claims);
            return claims;
        } catch (Exception e) {
            log.error("❌ JWT 파싱 오류 - 토큰: {}, 이유: {}", token, e.getMessage());
            throw e;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            boolean expired = claims.getExpiration().before(new Date());
            log.info("✅ JWT 만료 체크 - Expiration: {}, 현재 시간: {}, 만료 여부: {}",
                    claims.getExpiration(), new Date(), expired);
            return expired;
        } catch (Exception e) {
            log.error("❌ JWT 파싱 실패 - 이유: {}", e.getMessage());
            return true;
        }
    }
}
