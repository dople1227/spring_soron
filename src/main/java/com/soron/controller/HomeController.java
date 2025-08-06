package com.soron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 홈페이지 컨트롤러
 * 루트 경로 및 기본 페이지 처리
 */
@Controller
public class HomeController {

    /**
     * 루트 경로 - Sample 목록으로 리다이렉트
     * @return 리다이렉트 URL
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/samples";
    }

    /**
     * 헬스체크 엔드포인트
     * @return 상태 메시지
     */
    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK - Soron Application is running";
    }
}