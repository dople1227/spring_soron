# 데이터베이스 설정 가이드

## 📋 개요

Soron 프로젝트의 데이터베이스 설정 및 Sample 테이블 생성 가이드입니다.

## 📊 테이블 구조

### sample 테이블

| 컬럼명 | 데이터 타입 | 제약조건 | 설명 |
|--------|-------------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 샘플 ID |
| title | VARCHAR(255) | NOT NULL | 제목 |
| content | TEXT | NULL | 내용 |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'ACTIVE' | 상태 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 생성일시 |
| updated_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 수정일시 |

### 인덱스

- `PRIMARY KEY`: id
- `idx_sample_title`: title
- `idx_sample_status`: status  
- `idx_sample_created_at`: created_at
- `idx_sample_status_created_at`: status, created_at (복합 인덱스)
- `idx_sample_title_unique`: title (UNIQUE, status != 'DELETED' 조건)

## 🔧 애플리케이션 설정

### application.properties

```properties
# Database Configuration
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://your_ip:your_port/soron
spring.datasource.username=your_username
spring.datasource.password=your_password

# MyBatis Configuration
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.soron.sample.model
mybatis.configuration.map-underscore-to-camel-case=true
```

## 🧪 테스트 데이터

초기 설정 시 다음 샘플 데이터가 자동으로 삽입됩니다:

1. 첫 번째 샘플 (ACTIVE)
2. 두 번째 샘플 (ACTIVE)  
3. 비활성 샘플 (INACTIVE)
4. 긴 내용의 샘플 (ACTIVE)
5. API 테스트 샘플 (ACTIVE)

### 테이블 재생성

```sql
-- 기존 테이블 삭제 (주의: 데이터 손실)
DROP TABLE IF EXISTS sample;

-- DDL 스크립트 재실행
SOURCE src/main/resources/sql/sample_ddl.sql;
```

## 📚 참고사항

- MariaDB 10.2+ 버전에서 테스트됨
- UTF-8 문자셋 사용 (이모지 지원)
- 소프트 삭제 패턴 적용 (status = 'DELETED')
- 감사 필드 포함 (created_at, updated_at)