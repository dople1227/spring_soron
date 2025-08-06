package com.soron.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 국제화(i18n) 메시지 설정
 * 다국어 메시지 처리를 위한 MessageSource와 로케일 설정
 */
@Configuration
public class MessageConfig implements WebMvcConfigurer {
    
    /**
     * MessageSource Bean 등록
     * messages.properties 파일에서 메시지를 읽어오는 설정
     * 
     * @return ResourceBundleMessageSource 인스턴스
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages"); // messages.properties
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true); // 메시지를 찾지 못한 경우 키를 반환
        messageSource.setCacheSeconds(300); // 5분간 캐시
        return messageSource;
    }
    
    /**
     * 로케일 리졸버 설정
     * 세션 기반으로 로케일을 유지
     * 
     * @return SessionLocaleResolver 인스턴스
     */
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREAN); // 기본 로케일을 한국어로 설정
        return localeResolver;
    }
    
    /**
     * 로케일 변경 인터셉터 설정
     * URL 파라미터 'lang'으로 로케일 변경 가능
     * 예: ?lang=en, ?lang=ko
     * 
     * @return LocaleChangeInterceptor 인스턴스
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // ?lang=en 형태로 로케일 변경
        return interceptor;
    }
    
    /**
     * 로케일 변경 인터셉터를 등록
     */
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}