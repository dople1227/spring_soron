package com.soron.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI 3 설정
 * API 문서화를 위한 설정 클래스
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 설정 빈
     * @return OpenAPI 설정 객체
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Local Development Server"),
                        new Server().url("https://api.soron.com").description("Production Server")
                ));
    }

    /**
     * API 정보 설정
     * @return API 정보 객체
     */
    private Info apiInfo() {
        return new Info()
                .title("Soron API")
                .description("Soron 프로젝트 REST API 문서")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Soron Development Team")
                        .email("dev@soron.com")
                        .url("https://github.com/soron"));
    }
}