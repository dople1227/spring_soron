package com.soron.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 국제화 메시지 처리 유틸리티
 * MessageSource를 통해 다국어 메시지를 조회하고 파라미터 치환을 수행
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageResolver {
    
    private final MessageSource messageSource;
    
    /**
     * 메시지 키에 해당하는 현재 로케일의 메시지를 조회
     * 
     * @param key 메시지 키
     * @return 메시지 내용
     */
    public String getMessage(String key) {
        return getMessage(key, null, LocaleContextHolder.getLocale());
    }
    
    /**
     * 메시지 키와 파라미터로 현재 로케일의 메시지를 조회
     * 
     * @param key 메시지 키
     * @param args 치환할 파라미터 배열
     * @return 파라미터가 치환된 메시지 내용
     */
    public String getMessage(String key, Object... args) {
        return getMessage(key, args, LocaleContextHolder.getLocale());
    }
    
    /**
     * 메시지 키, 파라미터, 로케일로 메시지를 조회
     * 
     * @param key 메시지 키
     * @param args 치환할 파라미터 배열
     * @param locale 로케일
     * @return 파라미터가 치환된 메시지 내용
     */
    public String getMessage(String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            log.warn("메시지 조회 실패: key={}, locale={}, error={}", key, locale, e.getMessage());
            return key; // 메시지를 찾지 못한 경우 키 자체를 반환
        }
    }
    
    /**
     * 메시지 키와 기본값으로 현재 로케일의 메시지를 조회
     * 
     * @param key 메시지 키
     * @param defaultMessage 메시지를 찾지 못한 경우의 기본값
     * @return 메시지 내용 또는 기본값
     */
    public String getMessageOrDefault(String key, String defaultMessage) {
        return getMessageOrDefault(key, null, defaultMessage, LocaleContextHolder.getLocale());
    }
    
    /**
     * 메시지 키, 파라미터, 기본값으로 현재 로케일의 메시지를 조회
     * 
     * @param key 메시지 키
     * @param args 치환할 파라미터 배열
     * @param defaultMessage 메시지를 찾지 못한 경우의 기본값
     * @return 파라미터가 치환된 메시지 내용 또는 기본값
     */
    public String getMessageOrDefault(String key, Object[] args, String defaultMessage) {
        return getMessageOrDefault(key, args, defaultMessage, LocaleContextHolder.getLocale());
    }
    
    /**
     * 메시지 키, 파라미터, 기본값, 로케일로 메시지를 조회
     * 
     * @param key 메시지 키
     * @param args 치환할 파라미터 배열
     * @param defaultMessage 메시지를 찾지 못한 경우의 기본값
     * @param locale 로케일
     * @return 파라미터가 치환된 메시지 내용 또는 기본값
     */
    public String getMessageOrDefault(String key, Object[] args, String defaultMessage, Locale locale) {
        try {
            return messageSource.getMessage(key, args, defaultMessage, locale);
        } catch (Exception e) {
            log.warn("메시지 조회 실패: key={}, locale={}, error={}", key, locale, e.getMessage());
            return defaultMessage != null ? defaultMessage : key;
        }
    }
}