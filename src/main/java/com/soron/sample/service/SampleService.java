package com.soron.sample.service;

import com.soron.sample.dto.SampleDto;
import com.soron.sample.dto.SampleRequest;
import com.soron.sample.dto.SampleSearchDto;
import com.soron.common.response.PageResponse;

/**
 * Sample 서비스 인터페이스
 * 샘플 관련 비즈니스 로직을 정의하는 서비스 계층
 */
public interface SampleService {
    
    /**
     * 샘플 목록 조회 (페이징, 검색)
     * @param searchDto 검색 조건
     * @return 페이징된 샘플 목록
     */
    PageResponse<SampleDto> getSampleList(SampleSearchDto searchDto);
    
    /**
     * 샘플 단건 조회
     * @param id 샘플 ID
     * @return 샘플 정보
     */
    SampleDto getSampleById(Long id);
    
    /**
     * 샘플 생성
     * @param request 샘플 생성 요청
     * @return 생성된 샘플 정보
     */
    SampleDto createSample(SampleRequest request);
    
    /**
     * 샘플 수정
     * @param id 샘플 ID
     * @param request 샘플 수정 요청
     * @return 수정된 샘플 정보
     */
    SampleDto updateSample(Long id, SampleRequest request);
    
    /**
     * 샘플 삭제 (소프트 삭제)
     * @param id 샘플 ID
     */
    void deleteSample(Long id);
    
    /**
     * 제목 중복 확인
     * @param title 제목
     * @param excludeId 제외할 ID (수정 시 자기 자신 제외)
     * @return 중복 여부
     */
    boolean isTitleDuplicated(String title, Long excludeId);
}