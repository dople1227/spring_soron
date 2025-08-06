package com.soron.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 설정
 * 정적 리소스 설정 (JSP 뷰 리졸버는 application.properties에서 설정)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 핸들러 설정
     * @param registry 리소스 핸들러 레지스트리
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // CSS, JS, 이미지 등 정적 리소스 경로 설정
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // JSP에서 사용할 추가 리소스 경로
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
        
        // favicon.ico 처리를 위한 기본 정적 리소스 설정
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(31556926); // 1년
    }
}