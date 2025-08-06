package com.soron.sample.controller;

import com.soron.sample.dto.SampleDto;
import com.soron.sample.dto.SampleRequest;
import com.soron.sample.dto.SampleSearchDto;
import com.soron.sample.model.SampleStatus;
import com.soron.sample.service.SampleService;
import com.soron.common.response.ApiResponse;
import com.soron.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Sample REST API 컨트롤러
 * 샘플 관련 REST API 엔드포인트를 제공
 */
@Slf4j
@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
@Validated
@Tag(name = "Sample API", description = "샘플 관리 API")
public class SampleController {

    private final SampleService sampleService;

    /**
     * 샘플 목록 조회 (페이징, 검색)
     * @param title 제목 검색어
     * @param content 내용 검색어
     * @param status 상태
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @param sortBy 정렬 필드
     * @param sortDir 정렬 순서
     * @return 페이징된 샘플 목록
     */
    @GetMapping
    @Operation(summary = "샘플 목록 조회", description = "페이징과 검색 조건을 적용하여 샘플 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<PageResponse<SampleDto>>> getSampleList(
            @Parameter(description = "제목 검색어") @RequestParam(required = false) String title,
            @Parameter(description = "내용 검색어") @RequestParam(required = false) String content,
            @Parameter(description = "상태 (ACTIVE, INACTIVE, DELETED)") @RequestParam(required = false) String status,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @Parameter(description = "정렬 필드") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "정렬 순서") @RequestParam(defaultValue = "DESC") String sortDir) {
        
        log.debug("getSampleList 호출: title={}, content={}, status={}, page={}, size={}", 
                title, content, status, page, size);
        
        // String status를 SampleStatus enum으로 변환 (대소문자 구분 없이)
        SampleStatus statusEnum = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                statusEnum = SampleStatus.valueOf(status.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("올바르지 않은 상태 값: {}", status);
            }
        }
        
        SampleSearchDto searchDto = SampleSearchDto.builder()
                .title(title)
                .content(content)
                .status(statusEnum)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();
        
        PageResponse<SampleDto> result = sampleService.getSampleList(searchDto);
        return ResponseEntity.ok(ApiResponse.success(result, "샘플 목록 조회가 완료되었습니다."));
    }

    /**
     * 샘플 단건 조회
     * @param id 샘플 ID
     * @return 샘플 정보
     */
    @GetMapping("/{id}")
    @Operation(summary = "샘플 단건 조회", description = "ID로 특정 샘플의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<SampleDto>> getSampleById(
            @Parameter(description = "샘플 ID") @PathVariable @Min(1) Long id) {
        
        log.debug("getSampleById 호출: id={}", id);
        
        SampleDto result = sampleService.getSampleById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "샘플 조회가 완료되었습니다."));
    }

    /**
     * 샘플 생성
     * @param request 샘플 생성 요청
     * @return 생성된 샘플 정보
     */
    @PostMapping
    @Operation(summary = "샘플 생성", description = "새로운 샘플을 생성합니다.")
    public ResponseEntity<ApiResponse<SampleDto>> createSample(
            @Parameter(description = "샘플 생성 요청") @RequestBody @Valid SampleRequest request) {
        
        log.debug("createSample 호출: {}", request);
        
        SampleDto result = sampleService.createSample(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "샘플이 성공적으로 생성되었습니다."));
    }

    /**
     * 샘플 수정
     * @param id 샘플 ID
     * @param request 샘플 수정 요청
     * @return 수정된 샘플 정보
     */
    @PutMapping("/{id}")
    @Operation(summary = "샘플 수정", description = "기존 샘플의 정보를 수정합니다.")
    public ResponseEntity<ApiResponse<SampleDto>> updateSample(
            @Parameter(description = "샘플 ID") @PathVariable @Min(1) Long id,
            @Parameter(description = "샘플 수정 요청") @RequestBody @Valid SampleRequest request) {
        
        log.debug("updateSample 호출: id={}, request={}", id, request);
        
        SampleDto result = sampleService.updateSample(id, request);
        return ResponseEntity.ok(ApiResponse.success(result, "샘플이 성공적으로 수정되었습니다."));
    }

    /**
     * 샘플 삭제
     * @param id 샘플 ID
     * @return 삭제 완료 응답
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "샘플 삭제", description = "샘플을 삭제합니다. (소프트 삭제)")
    public ResponseEntity<ApiResponse<Void>> deleteSample(
            @Parameter(description = "샘플 ID") @PathVariable @Min(1) Long id) {
        
        log.debug("deleteSample 호출: id={}", id);
        
        sampleService.deleteSample(id);
        return ResponseEntity.ok(ApiResponse.success(null, "샘플이 성공적으로 삭제되었습니다."));
    }

    /**
     * 제목 중복 확인
     * @param title 확인할 제목
     * @param excludeId 제외할 ID (수정 시)
     * @return 중복 여부
     */
    @GetMapping("/check-title")
    @Operation(summary = "제목 중복 확인", description = "샘플 제목의 중복 여부를 확인합니다.")
    public ResponseEntity<ApiResponse<Boolean>> checkTitleDuplication(
            @Parameter(description = "확인할 제목") @RequestParam String title,
            @Parameter(description = "제외할 ID (수정 시)") @RequestParam(required = false) Long excludeId) {
        
        log.debug("checkTitleDuplication 호출: title={}, excludeId={}", title, excludeId);
        
        boolean isDuplicated = sampleService.isTitleDuplicated(title, excludeId);
        String message = isDuplicated ? "이미 사용 중인 제목입니다." : "사용 가능한 제목입니다.";
        
        return ResponseEntity.ok(ApiResponse.success(isDuplicated, message));
    }
}