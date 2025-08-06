package com.soron.sample.mapper;

import com.soron.sample.model.Sample;
import com.soron.sample.dto.SampleSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Sample MyBatis 매퍼 인터페이스
 * 샘플 데이터 CRUD 작업을 위한 데이터 접근 계층
 */
@Mapper
public interface SampleMapper {
    
    /**
     * 샘플 목록 조회 (페이징, 검색)
     * @param searchDto 검색 조건
     * @return 샘플 목록
     */
    List<Sample> selectSampleList(SampleSearchDto searchDto);
    
    /**
     * 샘플 총 개수 조회 (검색 조건 포함)
     * @param searchDto 검색 조건
     * @return 총 개수
     */
    long selectSampleCount(SampleSearchDto searchDto);
    
    /**
     * 샘플 단건 조회
     * @param id 샘플 ID
     * @return 샘플 정보
     */
    Sample selectSampleById(@Param("id") Long id);
    
    /**
     * 샘플 생성
     * @param sample 샘플 정보
     * @return 생성된 행 수
     */
    int insertSample(Sample sample);
    
    /**
     * 샘플 수정
     * @param sample 샘플 정보
     * @return 수정된 행 수
     */
    int updateSample(Sample sample);
    
    /**
     * 샘플 삭제 (물리적 삭제)
     * @param id 샘플 ID
     * @return 삭제된 행 수
     */
    int deleteSample(@Param("id") Long id);
    
    /**
     * 샘플 소프트 삭제 (상태를 DELETED로 변경)
     * @param id 샘플 ID
     * @return 수정된 행 수
     */
    int softDeleteSample(@Param("id") Long id);
    
    /**
     * 제목으로 샘플 존재 여부 확인
     * @param title 제목
     * @param excludeId 제외할 ID (수정 시 자기 자신 제외)
     * @return 존재 여부
     */
    boolean existsByTitle(@Param("title") String title, @Param("excludeId") Long excludeId);
}