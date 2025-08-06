package com.soron.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;
import com.soron.sample.model.SampleStatus;

import java.time.LocalDateTime;

/**
 * Sample 응답용 DTO
 * API 응답 시 사용되는 데이터 전송 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "샘플 응답 데이터")
public class SampleDto {
    
    @Schema(description = "샘플 ID", example = "1")
    private Long id;
    
    @Schema(description = "제목", example = "샘플 제목")
    private String title;
    
    @Schema(description = "내용", example = "샘플 내용입니다.")
    private String content;
    
    @Schema(description = "상태", example = "ACTIVE")
    private SampleStatus status;
    
    @Schema(description = "생성일시", example = "2025-08-05T22:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "수정일시", example = "2025-08-05T22:00:00")
    private LocalDateTime updatedAt;
}