package bst.bobsoolting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.OAuthFlows;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(info = @Info(title="BobSoolTing API 명세서",
        description = "BobSoolTing API 명세서",
        version = "v1"))
@Configuration
public class SwaggerConfiguration {

    @Bean
    @Profile("!Prod") // 운영환경에서는 제외
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("유저 관련 API")
                .pathsToMatch("/api/member/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi postApi() {
        return GroupedOpenApi.builder()
                .group("게시글 관련 API")
                .pathsToMatch("/api/post/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi commentApi() {
        return GroupedOpenApi.builder()
                .group("댓글 관련 API")
                .pathsToMatch("/api/comment/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi likeApi() {
        return GroupedOpenApi.builder()
                .group("좋아요 관련 API")
                .pathsToMatch("/api/like/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    @Profile("!Prod")
    public GroupedOpenApi replyApi() {
        return GroupedOpenApi.builder()
                .group("대댓글 관련 API")
                .pathsToMatch("/api/reply/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    public OpenApiCustomizer buildSecurityOpenApi() {
        return openApi -> openApi
                .addSecurityItem(new SecurityRequirement().addList("OAuth2"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("OAuth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                                                .tokenUrl("https://kauth.kakao.com/oauth/token")
                                        ))));
    }

}
