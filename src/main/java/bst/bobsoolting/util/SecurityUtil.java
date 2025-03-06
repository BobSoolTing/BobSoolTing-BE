package bst.bobsoolting.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class SecurityUtil {

    public static OAuth2User getCurrentOAuth2User() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            return (OAuth2User) authentication.getPrincipal();
        }
        return null;
    }

    public static Map<String, Object> getCurrentUserAttributes() {
        OAuth2User user = getCurrentOAuth2User();
        return user != null ? user.getAttributes() : null;
    }

    public static String getKakaoId() {
        Map<String, Object> attributes = getCurrentUserAttributes();
        if (attributes != null) {
            Object kakaoId = attributes.get("id");
            return kakaoId != null ? kakaoId.toString() : null;
        }
        return null;
    }
}
