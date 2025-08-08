package com.soron.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 시스템 상태 및 환경변수 확인용 컨트롤러
 * 개발 환경에서 .env 파일이 올바르게 로드되었는지 확인
 */
@Slf4j
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Value("${DB_URL:NOT_FOUND}")
    private String dbUrl;

    @Value("${DB_USERNAME:NOT_FOUND}")
    private String dbUsername;

    @Value("${REDIS_HOST:NOT_FOUND}")
    private String redisHost;

    @Value("${SERVER_PORT:NOT_FOUND}")
    private String serverPort;

    /**
     * 환경변수 로드 상태 확인
     * 민감한 정보(패스워드)는 제외하고 확인
     */
    @GetMapping("/env")
    public Map<String, Object> checkEnvironment() {
        Map<String, Object> result = new HashMap<>();
        
        result.put("status", "OK");
        result.put("message", "Environment variables status");
        
        Map<String, Object> envStatus = new HashMap<>();
        envStatus.put("DB_URL", maskSensitiveInfo(dbUrl));
        envStatus.put("DB_USERNAME", dbUsername.equals("NOT_FOUND") ? "❌ NOT_LOADED" : "✅ LOADED");
        envStatus.put("REDIS_HOST", redisHost.equals("NOT_FOUND") ? "❌ NOT_LOADED" : "✅ LOADED (" + redisHost + ")");
        envStatus.put("SERVER_PORT", serverPort.equals("NOT_FOUND") ? "❌ NOT_LOADED" : "✅ LOADED (" + serverPort + ")");
        
        result.put("environment", envStatus);
        
        log.info("Environment check requested - DB_URL: {}, Username: {}", 
                maskSensitiveInfo(dbUrl), 
                dbUsername.equals("NOT_FOUND") ? "NOT_FOUND" : "FOUND");
        
        return result;
    }

    /**
     * 민감한 정보를 마스킹처리
     */
    private String maskSensitiveInfo(String value) {
        if ("NOT_FOUND".equals(value) || value == null || value.isEmpty()) {
            return "❌ NOT_LOADED";
        }
        
        if (value.contains("://")) {
            // jdbc:mariadb://host:port/db 형태의 URL 마스킹
            return "✅ LOADED (jdbc:mariadb://***:****/****)";
        }
        
        return "✅ LOADED";
    }
}