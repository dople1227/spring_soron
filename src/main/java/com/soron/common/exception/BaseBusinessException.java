package com.soron.common.exception;

import lombok.Getter;

/**
 * 비즈니스 예외의 기본 클래스
 * 모든 커스텀 비즈니스 예외는 이 클래스를 상속받아 구현
 */
@Getter
public abstract class BaseBusinessException extends RuntimeException {
    
    private final String errorCode;
    private final Object[] args;
    
    /**
     * 메시지만 받는 생성자
     * 
     * @param message 예외 메시지
     */
    protected BaseBusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.args = null;
    }
    
    /**
     * 메시지와 에러 코드를 받는 생성자
     * 
     * @param message 예외 메시지
     * @param errorCode 에러 코드
     */
    protected BaseBusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.args = null;
    }
    
    /**
     * 메시지 키, 에러 코드, 파라미터를 받는 생성자
     * 메시지 국제화를 위해 사용
     * 
     * @param messageKey 메시지 키
     * @param errorCode 에러 코드
     * @param args 메시지 파라미터
     */
    protected BaseBusinessException(String messageKey, String errorCode, Object... args) {
        super(messageKey); // messageKey를 임시로 message로 사용
        this.errorCode = errorCode;
        this.args = args;
    }
    
    /**
     * 메시지와 원인을 받는 생성자
     * 
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    protected BaseBusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BUSINESS_ERROR";
        this.args = null;
    }
    
    /**
     * 메시지, 에러 코드, 원인을 받는 생성자
     * 
     * @param message 예외 메시지
     * @param errorCode 에러 코드
     * @param cause 원인 예외
     */
    protected BaseBusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = null;
    }
    
    /**
     * 메시지 키인지 확인
     * args가 있으면 messageKey로 간주
     * 
     * @return 메시지 키 여부
     */
    public boolean isMessageKey() {
        return args != null;
    }
    
    /**
     * 메시지 키 반환 (getMessage()와 동일하지만 의미를 명확히 하기 위함)
     * 
     * @return 메시지 키
     */
    public String getMessageKey() {
        return getMessage();
    }
}