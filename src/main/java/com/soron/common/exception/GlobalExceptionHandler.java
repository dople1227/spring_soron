package com.soron.common.exception;

import com.soron.common.response.ApiResponse;
import com.soron.common.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 * 애플리케이션에서 발생하는 예외를 통합적으로 처리
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final MessageResolver messageResolver;

    /**
     * IllegalArgumentException 처리
     * 잘못된 인자 전달 시 발생하는 예외
     */
    /**
     * EntityNotFoundException 처리
     * 엔티티를 찾을 수 없는 경우 발생하는 예외
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("EntityNotFoundException 발생: {}", e.getMessage());
        
        String message = resolveExceptionMessage(e);
        ApiResponse<Void> response = ApiResponse.error(message, e.getErrorCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * DuplicateEntityException 처리
     * 엔티티 중복 시 발생하는 예외
     */
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEntityException(DuplicateEntityException e) {
        log.warn("DuplicateEntityException 발생: {}", e.getMessage());
        
        String message = resolveExceptionMessage(e);
        ApiResponse<Void> response = ApiResponse.error(message, e.getErrorCode());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    /**
     * IllegalArgumentException 처리
     * 잘못된 인자 전달 시 발생하는 예외
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException 발생: {}", e.getMessage());
        
        String message = messageResolver.getMessageOrDefault("common.error.badrequest", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(message, "BAD_REQUEST");
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * MethodArgumentNotValidException 처리
     * Bean Validation 실패 시 발생하는 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {
        log.warn("Validation 예외 발생: {}", e.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String message = messageResolver.getMessage("common.error.validation");
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message(message)
                .data(errors)
                .errorCode("VALIDATION_FAILED")
                .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * RuntimeException 처리
     * 예상치 못한 런타임 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException 발생: {}", e.getMessage(), e);
        
        String message = messageResolver.getMessage("common.error.internal");
        ApiResponse<Void> response = ApiResponse.error(message, "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * NoResourceFoundException 처리
     * 정적 리소스를 찾을 수 없는 경우 (favicon.ico 등)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(
            NoResourceFoundException e, HttpServletRequest request) {
        
        String requestURI = request.getRequestURI();
        log.debug("정적 리소스를 찾을 수 없음: {}", requestURI);
        
        // favicon.ico나 기타 정적 리소스는 404를 반환하고 로그는 DEBUG 레벨로
        if (requestURI.contains("favicon.ico") || requestURI.contains(".css") || 
            requestURI.contains(".js") || requestURI.contains(".png") || 
            requestURI.contains(".jpg") || requestURI.contains(".gif")) {
            return ResponseEntity.notFound().build();
        }
        
        // API 요청인 경우에만 JSON 응답
        if (requestURI.startsWith("/api/")) {
            log.warn("API 리소스를 찾을 수 없음: {}", requestURI);
            String message = messageResolver.getMessage("common.error.notfound");
            ApiResponse<Void> response = ApiResponse.error(message, "RESOURCE_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        // 일반 페이지 요청은 404 페이지로
        return ResponseEntity.notFound().build();
    }

    /**
     * Exception 처리
     * 모든 예외의 최상위 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
        log.error("예상치 못한 예외 발생: {}", e.getMessage(), e);
        
        // API 요청이 아닌 경우 일반 오류 페이지로 리다이렉트
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/api/")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        String message = messageResolver.getMessage("common.error.unknown");
        ApiResponse<Void> response = ApiResponse.error(message, "UNKNOWN_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    /**
     * BaseBusinessException의 메시지를 해석하는 헬퍼 메소드
     * 
     * @param exception 비즈니스 예외
     * @return 해석된 메시지
     */
    private String resolveExceptionMessage(BaseBusinessException exception) {
        if (exception.isMessageKey()) {
            // 메시지 키와 파라미터가 있는 경우 국제화 메시지 조회
            return messageResolver.getMessageOrDefault(
                exception.getMessageKey(), 
                exception.getArgs(),
                exception.getMessage()
            );
        } else {
            // 일반 메시지인 경우 그대로 반환
            return exception.getMessage();
        }
    }
}