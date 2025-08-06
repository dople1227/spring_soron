package com.soron.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;
import com.soron.sample.model.SampleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Sample 요청용 DTO
 * 생성 및 수정 요청 시 사용되는 데이터 전송 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "샘플 요청 데이터")
public class SampleRequest {
    
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
    @Schema(description = "제목", example = "새로운 샘플 제목", required = true)
    private String title;
    
    @Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    @Schema(description = "내용", example = "새로운 샘플 내용입니다.")
    private String content;
    
    @Schema(description = "상태", example = "ACTIVE")
    private SampleStatus status;
}