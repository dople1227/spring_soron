package com.soron.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Sample 엔티티
 * 샘플 데이터를 나타내는 도메인 모델
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sample {
    
    /**
     * 샘플 ID (기본키)
     */
    private Long id;
    
    /**
     * 제목
     */
    private String title;
    
    /**
     * 내용
     */
    private String content;
    
    /**
     * 상태 (ACTIVE, INACTIVE, DELETED)
     */
    private SampleStatus status;
    
    /**
     * 생성일시
     */
    private LocalDateTime createdAt;
    
    /**
     * 수정일시
     */
    private LocalDateTime updatedAt;
}