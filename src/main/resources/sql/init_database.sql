-- ==========================================
-- 데이터베이스 초기화 스크립트
-- Soron 프로젝트 데이터베이스 설정
-- ==========================================

-- 1. 데이터베이스 생성 (필요한 경우)
CREATE DATABASE IF NOT EXISTS soron 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 2. 데이터베이스 사용
USE soron;

-- 3. 사용자 생성 및 권한 부여 (필요한 경우)
-- CREATE USER IF NOT EXISTS 'your_name'@'%' IDENTIFIED BY 'your_password';
-- GRANT ALL PRIVILEGES ON soron.* TO 'your_name'@'%';
-- FLUSH PRIVILEGES;

-- 4. 시간대 설정 확인
-- SELECT @@global.time_zone, @@session.time_zone;

-- 5. 문자셋 확인
-- SHOW VARIABLES LIKE 'character_set%';
-- SHOW VARIABLES LIKE 'collation%';

-- ==========================================
-- 데이터베이스 정보 확인 쿼리
-- ==========================================

-- 현재 데이터베이스 정보
SELECT 
    SCHEMA_NAME as '데이터베이스명',
    DEFAULT_CHARACTER_SET_NAME as '기본_문자셋',
    DEFAULT_COLLATION_NAME as '기본_콜레이션'
FROM information_schema.SCHEMATA 
WHERE SCHEMA_NAME = 'soron';

-- 테이블 목록 (생성 후 확인용)
-- SELECT 
--     TABLE_NAME as '테이블명',
--     TABLE_COMMENT as '테이블_설명',
--     CREATE_TIME as '생성시간'
-- FROM information_schema.TABLES 
-- WHERE TABLE_SCHEMA = 'soron'
-- ORDER BY CREATE_TIME DESC;