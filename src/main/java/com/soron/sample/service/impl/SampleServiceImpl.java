package com.soron.sample.service.impl;

import com.soron.sample.dto.SampleDto;
import com.soron.sample.dto.SampleRequest;
import com.soron.sample.dto.SampleSearchDto;
import com.soron.sample.mapper.SampleMapper;
import com.soron.sample.model.Sample;
import com.soron.sample.model.SampleStatus;
import com.soron.sample.service.SampleService;
import com.soron.common.response.PageResponse;
import com.soron.common.exception.EntityNotFoundException;
import com.soron.common.exception.DuplicateEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sample 서비스 구현 클래스
 * 샘플 관련 비즈니스 로직을 구현하는 서비스 계층
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleServiceImpl implements SampleService {

    private final SampleMapper sampleMapper;

    /**
     * 샘플 목록 조회 (페이징, 검색)
     * @param searchDto 검색 조건
     * @return 페이징된 샘플 목록
     */
    @Override
    public PageResponse<SampleDto> getSampleList(SampleSearchDto searchDto) {
        log.debug("getSampleList 호출: {}", searchDto);
        
        // 검색 조건에 맞는 샘플 목록 조회
        List<Sample> samples = sampleMapper.selectSampleList(searchDto);
        long totalCount = sampleMapper.selectSampleCount(searchDto);
        
        // Entity를 DTO로 변환
        List<SampleDto> sampleDtos = samples.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return PageResponse.of(sampleDtos, searchDto.getPage(), searchDto.getSize(), totalCount);
    }

    /**
     * 샘플 단건 조회
     * @param id 샘플 ID
     * @return 샘플 정보
     * @throws IllegalArgumentException 샘플을 찾을 수 없는 경우
     */
    @Override
    public SampleDto getSampleById(Long id) {
        log.debug("getSampleById 호출: {}", id);
        
        Sample sample = sampleMapper.selectSampleById(id);
        if (sample == null) {
            throw new EntityNotFoundException("sample.notfound", id);
        }
        
        return convertToDto(sample);
    }

    /**
     * 샘플 생성
     * @param request 샘플 생성 요청
     * @return 생성된 샘플 정보
     * @throws IllegalArgumentException 제목이 중복되는 경우
     */
    @Override
    @Transactional
    public SampleDto createSample(SampleRequest request) {
        log.debug("createSample 호출: {}", request);
        
        // 제목 중복 확인
        if (isTitleDuplicated(request.getTitle(), null)) {
            throw new DuplicateEntityException("sample.title.duplicate", request.getTitle());
        }
        
        // Request를 Entity로 변환
        Sample sample = Sample.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus() != null ? 
                        request.getStatus() : SampleStatus.ACTIVE)
                .build();
        
        // 샘플 생성
        int result = sampleMapper.insertSample(sample);
        if (result != 1) {
            throw new RuntimeException("sample.create.failed");
        }
        
        log.info("샘플 생성 완료: ID={}, 제목={}", sample.getId(), sample.getTitle());
        return convertToDto(sample);
    }

    /**
     * 샘플 수정
     * @param id 샘플 ID
     * @param request 샘플 수정 요청
     * @return 수정된 샘플 정보
     * @throws IllegalArgumentException 샘플을 찾을 수 없거나 제목이 중복되는 경우
     */
    @Override
    @Transactional
    public SampleDto updateSample(Long id, SampleRequest request) {
        log.debug("updateSample 호출: ID={}, request={}", id, request);
        
        // 기존 샘플 조회
        Sample existingSample = sampleMapper.selectSampleById(id);
        if (existingSample == null) {
            throw new EntityNotFoundException("sample.notfound", id);
        }
        
        // 제목 중복 확인 (자기 자신 제외)
        if (isTitleDuplicated(request.getTitle(), id)) {
            throw new DuplicateEntityException("sample.title.duplicate", request.getTitle());
        }
        
        // 수정할 샘플 정보 설정
        Sample sample = Sample.builder()
                .id(id)
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus() != null ? 
                        request.getStatus() : existingSample.getStatus())
                .build();
        
        // 샘플 수정
        int result = sampleMapper.updateSample(sample);
        if (result != 1) {
            throw new RuntimeException("sample.update.failed");
        }
        
        log.info("샘플 수정 완료: ID={}, 제목={}", id, sample.getTitle());
        
        // 수정된 샘플 조회 후 반환
        return getSampleById(id);
    }

    /**
     * 샘플 삭제 (소프트 삭제)
     * @param id 샘플 ID
     * @throws IllegalArgumentException 샘플을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public void deleteSample(Long id) {
        log.debug("deleteSample 호출: {}", id);
        
        // 기존 샘플 존재 확인
        Sample existingSample = sampleMapper.selectSampleById(id);
        if (existingSample == null) {
            throw new EntityNotFoundException("sample.notfound", id);
        }
        
        // 소프트 삭제 실행
        int result = sampleMapper.softDeleteSample(id);
        if (result != 1) {
            throw new RuntimeException("sample.delete.failed");
        }
        
        log.info("샘플 삭제 완료: ID={}, 제목={}", id, existingSample.getTitle());
    }

    /**
     * 제목 중복 확인
     * @param title 제목
     * @param excludeId 제외할 ID (수정 시 자기 자신 제외)
     * @return 중복 여부
     */
    @Override
    public boolean isTitleDuplicated(String title, Long excludeId) {
        if (!StringUtils.hasText(title)) {
            return false;
        }
        return sampleMapper.existsByTitle(title, excludeId);
    }

    /**
     * Sample Entity를 SampleDto로 변환
     * @param sample Sample 엔티티
     * @return SampleDto
     */
    private SampleDto convertToDto(Sample sample) {
        return SampleDto.builder()
                .id(sample.getId())
                .title(sample.getTitle())
                .content(sample.getContent())
                .status(sample.getStatus())
                .createdAt(sample.getCreatedAt())
                .updatedAt(sample.getUpdatedAt())
                .build();
    }
}