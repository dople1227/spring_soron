package com.soron.sample.model;

/**
 * Sample 상태 열거형
 * 샘플 데이터의 상태를 정의
 */
public enum SampleStatus {
    
    /**
     * 활성 상태
     */
    ACTIVE("활성"),
    
    /**
     * 비활성 상태
     */
    INACTIVE("비활성"),
    
    /**
     * 삭제 상태
     */
    DELETED("삭제");
    
    private final String description;
    
    SampleStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}