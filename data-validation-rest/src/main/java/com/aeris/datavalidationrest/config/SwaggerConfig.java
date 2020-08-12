package com.aeris.datavalidationrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket instrumentsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Instruments")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aeris.datavalidationrest.instruments"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket sessionsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Sessions")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aeris.datavalidationrest.sessions"))
                .paths(PathSelectors.any())
                .build();
    }
}
