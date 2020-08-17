package com.aeris.datavalidationrest.config;

import com.google.common.collect.Lists;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
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
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION).build();
    }

    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}

