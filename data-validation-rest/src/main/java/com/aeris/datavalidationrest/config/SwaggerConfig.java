package com.aeris.datavalidationrest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Value("${keycloak.auth-server-url}")
    private String AUTH_SERVER;

    @Value("${keycloak.credentials.secret}")
    private String CLIENT_SECRET;

    @Value("${keycloak.resource}")
    private String CLIENT_ID;

    @Value("${keycloak.realm}")
    private String REALM;

    private static final String FLAG_PATH = "(?!/flags).+";
    private static final String ERROR_PATH = "(?!/error).+";
    private static final String PARAMETERS_PATH = "(?!/parameters).+";
    private static final String GROUND_DATA_PATH = "(?!/ground-data).+";
    private static final String BASE_PACKAGE = "com.aeris.datavalidationrest";
    private static final String DATA_INFORMATION_PATH = "(?!/data-information).+";

    private static final String OAUTH_NAME = "spring_oauth";
    private static final String ALLOWED_PATHS = "/flags/.*";
    private static final String GROUP_NAME = "datavalidation";
    private static final String TITLE = "Datavalidation REST API";
    private static final String DESCRIPTION = "Aeris Data validation tool.";
    private static final String VERSION = "1.0.0";

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_NAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.regex(ERROR_PATH))
                .paths(PathSelectors.regex(FLAG_PATH))
                .paths(PathSelectors.regex(PARAMETERS_PATH))
                .paths(PathSelectors.regex(GROUND_DATA_PATH))
                .paths(PathSelectors.regex(DATA_INFORMATION_PATH))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).version(VERSION).build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .appName(GROUP_NAME)
                .scopeSeparator(" ")
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType =
                new AuthorizationCodeGrantBuilder()
                        .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/token", GROUP_NAME))
                        .tokenRequestEndpoint(
                                new TokenRequestEndpoint(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/auth", CLIENT_ID, CLIENT_SECRET))
                        .build();

        SecurityScheme oauth =
                new OAuthBuilder()
                        .name(OAUTH_NAME)
                        .grantTypes(Arrays.asList(grantType))
                        .scopes(Arrays.asList(scopes()))
                        .build();
        return oauth;
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                //new AuthorizationScope("user", "for CRUD operations"),
                //new AuthorizationScope("read", "for read operations"),
                //new AuthorizationScope("write", "for write operations"),
                //new AuthorizationScope("datavalidation-rest", "Access datavalidation-rest API")
        };
        return scopes;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference(OAUTH_NAME, scopes())))
                .forPaths(PathSelectors.regex(ALLOWED_PATHS))
                .build();
    }
}

