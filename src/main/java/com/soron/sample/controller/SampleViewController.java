package com.soron.sample.controller;

import com.soron.sample.dto.SampleDto;
import com.soron.sample.dto.SampleSearchDto;
import com.soron.sample.service.SampleService;
import com.soron.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Sample JSP 뷰 컨트롤러
 * JSP 페이지 렌더링을 위한 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleViewController {

    private final SampleService sampleService;

    /**
     * 샘플 목록 페이지
     * @param searchDto 검색 조건
     * @param model 모델 객체
     * @return 목록 페이지 뷰
     */
    @GetMapping
    public String sampleList(@ModelAttribute SampleSearchDto searchDto, Model model) {
        // 기본값 설정
        if (searchDto.getPage() == null) searchDto.setPage(0);
        if (searchDto.getSize() == null) searchDto.setSize(10);
        if (searchDto.getSortBy() == null) searchDto.setSortBy("createdAt");
        if (searchDto.getSortDir() == null) searchDto.setSortDir("DESC");
        
        PageResponse<SampleDto> samples = sampleService.getSampleList(searchDto);
        
        model.addAttribute("samples", samples);
        model.addAttribute("searchDto", searchDto);
        
        return "samples/list";
    }

    /**
     * 샘플 상세보기 페이지
     * @param id 샘플 ID
     * @param model 모델 객체
     * @return 상세보기 페이지 뷰
     */
    @GetMapping("/{id}")
    public String sampleDetail(@PathVariable Long id, Model model) {
        try {
            SampleDto sample = sampleService.getSampleById(id);
            model.addAttribute("sample", sample);
            return "samples/detail";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "samples/error";
        }
    }

    /**
     * 샘플 등록 폼 페이지
     * @param model 모델 객체
     * @return 등록 폼 페이지 뷰
     */
    @GetMapping("/new")
    public String sampleCreateForm(Model model) {
        model.addAttribute("isEdit", false);
        return "samples/form";
    }

    /**
     * 샘플 수정 폼 페이지
     * @param id 샘플 ID
     * @param model 모델 객체
     * @return 수정 폼 페이지 뷰
     */
    @GetMapping("/{id}/edit")
    public String sampleEditForm(@PathVariable Long id, Model model) {
        try {
            SampleDto sample = sampleService.getSampleById(id);
            model.addAttribute("sample", sample);
            model.addAttribute("isEdit", true);
            return "samples/form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "samples/error";
        }
    }

    /**
     * 샘플 삭제 처리
     * @param id 샘플 ID
     * @param redirectAttributes 리다이렉트 속성
     * @return 리다이렉트 URL
     */
    @PostMapping("/{id}/delete")
    public String deleteSample(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.debug("deleteSample 호출: id={}", id);
        
        try {
            sampleService.deleteSample(id);
            redirectAttributes.addFlashAttribute("successMessage", "샘플이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "샘플 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "redirect:/samples";
    }
}