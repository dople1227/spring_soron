package com.soron.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 전역 날짜 포맷팅 설정
 * JSP에서 사용할 날짜 포맷팅 유틸리티를 제공
 */
@ControllerAdvice
public class DateFormatConfig {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private static final DateTimeFormatter KOREAN_DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
    
    /**
     * JSP에서 사용할 날짜 포맷팅 유틸리티를 전역적으로 제공
     */
    @ModelAttribute("dateUtil")
    public DateUtil getDateUtil() {
        return new DateUtil();
    }
    
    /**
     * 날짜 포맷팅 유틸리티 클래스
     */
    public static class DateUtil {
        
        /**
         * LocalDateTime을 yyyy-MM-dd HH:mm 형식으로 포맷팅
         */
        public String format(LocalDateTime dateTime) {
            if (dateTime == null) {
                return "";
            }
            return dateTime.format(DATE_TIME_FORMATTER);
        }
        
        /**
         * LocalDateTime을 한국어 형식으로 포맷팅
         */
        public String formatKorean(LocalDateTime dateTime) {
            if (dateTime == null) {
                return "";
            }
            return dateTime.format(KOREAN_DATE_TIME_FORMATTER);
        }
        
    }
}