package bst.bobsoolting.security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @PostConstruct
    public void init() {
        log.info("✅ JwtAuthenticationFilter가 SecurityFilterChain에 추가됨");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            if (jwtTokenProvider.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Access Token이 만료되었습니다. Refresh Token을 사용하세요.");
                return;
            }

            Claims claims = jwtTokenProvider.parseToken(token);
            log.info("Parsed claims: {}", claims);

            String kakaoId = claims.getSubject();

            UserDetails userDetails = User.withUsername(kakaoId).password("").roles("USER").build();
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("Current Authentication: {}", auth);
        }

        chain.doFilter(request, response);
    }
}
