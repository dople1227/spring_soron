-- ==========================================
-- Sample 테이블 DDL 스크립트
-- Soron 프로젝트용 샘플 데이터 관리 테이블
-- ==========================================

-- Sample 테이블 생성
CREATE TABLE IF NOT EXISTS sample (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '샘플 ID (기본키)',
    title VARCHAR(255) NOT NULL COMMENT '제목',
    content TEXT COMMENT '내용',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '상태 (ACTIVE, INACTIVE, DELETED)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    
    -- 인덱스를 테이블 생성과 함께 정의
    INDEX idx_sample_title (title),
    INDEX idx_sample_status (status),
    INDEX idx_sample_created_at (created_at),
    INDEX idx_sample_status_created_at (status, created_at)
    
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci 
  COMMENT='샘플 데이터 관리 테이블';

-- 테스트용 샘플 데이터 삽입
INSERT INTO sample (title, content, status) VALUES 
('첫 번째 샘플', '이것은 첫 번째 샘플 데이터입니다. 테스트용으로 작성되었습니다.', 'ACTIVE'),
('두 번째 샘플', '두 번째 샘플 데이터입니다. 더 많은 내용을 포함하고 있습니다.', 'ACTIVE'),
('비활성 샘플', '이 샘플은 비활성 상태입니다.', 'INACTIVE'),
('긴 내용의 샘플', '이것은 긴 내용을 가진 샘플입니다. 여러 줄에 걸쳐서 작성된 내용으로, 실제 사용 환경에서 어떻게 표시되는지 확인하기 위한 목적으로 작성되었습니다. 이런 식으로 긴 텍스트가 들어갔을 때 UI에서 어떻게 처리되는지 테스트할 수 있습니다.', 'ACTIVE'),
('API 테스트 샘플', 'REST API 테스트를 위한 샘플 데이터입니다.', 'ACTIVE');