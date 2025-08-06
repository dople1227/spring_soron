package com.soron.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 페이징 응답 구조
 * 목록 조회 시 사용되는 페이징 정보를 포함한 응답 객체
 * @param <T> 목록 데이터 타입
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "페이징 응답 구조")
public class PageResponse<T> {
    
    @Schema(description = "목록 데이터")
    private List<T> content;
    
    @Schema(description = "현재 페이지 번호", example = "0")
    private int page;
    
    @Schema(description = "페이지 크기", example = "10")
    private int size;
    
    @Schema(description = "전체 데이터 수", example = "100")
    private long totalElements;
    
    @Schema(description = "전체 페이지 수", example = "10")
    private int totalPages;
    
    @Schema(description = "첫 번째 페이지 여부", example = "true")
    private boolean first;
    
    @Schema(description = "마지막 페이지 여부", example = "false")
    private boolean last;
    
    /**
     * 페이징 응답 생성
     * @param content 목록 데이터
     * @param page 현재 페이지
     * @param size 페이지 크기
     * @param totalElements 전체 데이터 수
     * @param <T> 데이터 타입
     * @return 페이징 응답 객체
     */
    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return PageResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(page == 0)
                .last(page >= totalPages - 1)
                .build();
    }
}