package bst.bobsoolting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi memberApi() {

        String[] paths = {"/api/member/**"};

        return GroupedOpenApi
                .builder()
                .group("유저 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi postApi() {

        String[] paths = {"/api/post/**"};

        return GroupedOpenApi
                .builder()
                .group("게시글 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi commentApi() {

        String[] paths = {"/api/comment/**"};

        return GroupedOpenApi
                .builder()
                .group("댓글 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi likeApi() {

        String[] paths = {"/api/like/**"};

        return GroupedOpenApi
                .builder()
                .group("좋아요 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    @Bean
    @Profile("!Prod") // 운영환경은 제외
    public GroupedOpenApi replyApi() {

        String[] paths = {"/api/reply/**"};

        return GroupedOpenApi
                .builder()
                .group("대댓글 관련 api")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(buildSecurityOpenApi()).build();
    }

    public OpenApiCustomizer buildSecurityOpenApi() {
        // jwt token을 한번 설정하면 header에 값을 넣어주는 코드
        return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                        .scheme("bearer"));
    }
}
