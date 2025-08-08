package com.soron.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * .env 파일을 로드하는 설정 클래스
 * 로컬 개발 환경에서 환경변수를 쉽게 관리할 수 있도록 지원
 */
@Slf4j
@Configuration
public class DotEnvConfig {

    private final Environment environment;

    public DotEnvConfig(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadDotEnv() {
        try {
            // .env 파일이 존재하는 경우에만 로드
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            // 환경변수를 시스템 프로퍼티로 설정
            dotenv.entries().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                
                // 시스템 프로퍼티에 아직 설정되지 않은 경우에만 설정
                if (System.getProperty(key) == null) {
                    System.setProperty(key, value);
                    log.debug("Loaded environment variable from .env: {} = [PROTECTED]", key);
                }
            });

            log.info(".env 파일이 성공적으로 로드되었습니다.");
            
        } catch (Exception e) {
            log.warn(".env 파일 로드 중 오류 발생 (선택사항이므로 계속 진행): {}", e.getMessage());
        }
    }
}