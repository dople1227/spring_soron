package com.soron.common.exception;

/**
 * 엔티티 중복 시 발생하는 예외
 * 주로 유니크 제약조건 위반 시에 사용
 */
public class DuplicateEntityException extends BaseBusinessException {
    
    private static final String DEFAULT_ERROR_CODE = "DUPLICATE_ENTITY";
    
    /**
     * 기본 생성자
     * 
     * @param message 예외 메시지
     */
    public DuplicateEntityException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }
    
    /**
     * 메시지 키와 파라미터를 받는 생성자
     * 
     * @param messageKey 메시지 키
     * @param args 메시지 파라미터
     */
    public DuplicateEntityException(String messageKey, Object... args) {
        super(messageKey, DEFAULT_ERROR_CODE, args);
    }
    
    /**
     * 메시지와 에러 코드를 받는 생성자
     * 
     * @param message 예외 메시지
     * @param errorCode 에러 코드
     */
    public DuplicateEntityException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    /**
     * 메시지 키, 에러 코드, 파라미터를 받는 생성자
     * 
     * @param messageKey 메시지 키
     * @param errorCode 에러 코드
     * @param args 메시지 파라미터
     */
    public DuplicateEntityException(String messageKey, String errorCode, Object... args) {
        super(messageKey, errorCode, args);
    }
}