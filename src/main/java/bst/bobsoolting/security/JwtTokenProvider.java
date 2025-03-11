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
        return generateToken(kakaoId, accessTokenValidityInMilliseconds);
    }

    // ✅ Refresh Token 생성
    public String generateRefreshToken(String kakaoId) {
        return generateToken(kakaoId, refreshTokenValidityInMilliseconds);
    }

    private String generateToken(String kakaoId, long validityPeriod) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityPeriod);

        return Jwts.builder()
                .setSubject(kakaoId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
