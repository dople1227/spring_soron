package com.soron.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;
import com.soron.sample.model.SampleStatus;

/**
 * Sample 검색용 DTO
 * 목록 조회 및 검색 시 사용되는 데이터 전송 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "샘플 검색 조건")
public class SampleSearchDto {
    
    @Schema(description = "제목 검색어", example = "샘플")
    private String title;
    
    @Schema(description = "내용 검색어", example = "내용")
    private String content;
    
    @Schema(description = "상태", example = "ACTIVE")
    private SampleStatus status;
    
    @Schema(description = "페이지 번호 (0부터 시작)", example = "0", defaultValue = "0")
    @Builder.Default
    private Integer page = 0;
    
    @Schema(description = "페이지 크기", example = "10", defaultValue = "10")
    @Builder.Default
    private Integer size = 10;
    
    @Schema(description = "정렬 필드", example = "createdAt", defaultValue = "createdAt")
    @Builder.Default
    private String sortBy = "createdAt";
    
    @Schema(description = "정렬 순서", example = "DESC", allowableValues = {"ASC", "DESC"}, defaultValue = "DESC")
    @Builder.Default
    private String sortDir = "DESC";
    
    /**
     * 페이징을 위한 offset 계산
     * @return 시작 인덱스
     */
    public int getOffset() {
        return page * size;
    }
}