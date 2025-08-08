# 보안 가이드 (Security Guide)

## ⚠️ 중요한 보안 주의사항

### 1. 환경변수 사용 (Environment Variables)

**절대 하지 말 것:**
```properties
# ❌ 절대 이렇게 하지 마세요
spring.datasource.password=your-real-password
spring.datasource.url=jdbc:mariadb://real-server:3306/database
```

**올바른 방법:**
```properties
# ✅ 환경변수 사용
spring.datasource.password=${DB_PASSWORD:default-value}
spring.datasource.url=${DB_URL:jdbc:mariadb://localhost:3306/soron}
```

### 2. Spring Profiles 활용

#### 로컬 개발 환경
```bash
# application-local.properties 사용 (Git에 추가하지 마세요!)
java -jar app.jar --spring.profiles.active=local
```

#### 프로덕션 환경
```bash
# 환경변수 설정
export DB_URL="jdbc:mariadb://prod-server:3306/database"
export DB_USERNAME="prod-user"
export DB_PASSWORD="secure-password"
export SPRING_PROFILES_ACTIVE="prod"

java -jar app.jar
```

### 3. Docker 환경

```dockerfile
# Dockerfile
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_URL=jdbc:mariadb://db:3306/soron
ENV DB_USERNAME=soron
ENV DB_PASSWORD=${DB_PASSWORD}
```

```yaml
# docker-compose.yml
version: '3.8'
services:
  app:
    build: .
    environment:
      - DB_PASSWORD=${DB_PASSWORD}
      - REDIS_HOST=redis
    env_file:
      - .env  # .gitignore에 포함되어야 함
```

### 4. 필수 환경변수 목록

| 변수명 | 설명 | 예시 |
|--------|------|------|
| `DB_URL` | 데이터베이스 연결 URL | `jdbc:mariadb://localhost:3306/soron` |
| `DB_USERNAME` | 데이터베이스 사용자명 | `soron_user` |
| `DB_PASSWORD` | 데이터베이스 패스워드 | `secure_password123` |
| `REDIS_HOST` | Redis 서버 주소 | `localhost` |
| `REDIS_PORT` | Redis 포트 | `6379` |
| `SERVER_PORT` | 애플리케이션 포트 | `9090` |

### 5. GitHub 보안 조치

#### 이미 커밋된 민감정보 제거
```bash
# Git 히스토리에서 완전 제거 (주의: 강제 푸시 필요)
git filter-branch --force --index-filter \
'git rm --cached --ignore-unmatch src/main/resources/application.properties' \
--prune-empty --tag-name-filter cat -- --all

# 모든 브랜치에 강제 푸시
git push --force --all
git push --force --tags
```

#### 대안: BFG Repo-Cleaner 사용
```bash
# BFG 다운로드 후
java -jar bfg.jar --delete-files application.properties
git reflog expire --expire=now --all && git gc --prune=now --aggressive
git push --force
```

### 6. 추가 보안 권장사항

#### application.properties 보안 설정
```properties
# 에러 정보 노출 방지
server.error.include-stacktrace=never
server.error.include-message=never
server.error.include-binding-errors=never

# 액추에이터 보안
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=never
```

#### HTTPS 설정 (프로덕션)
```properties
# SSL 인증서 설정
server.ssl.key-store=${SSL_KEYSTORE_PATH}
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12
server.port=8443
```

### 7. 모니터링 및 감사

- 데이터베이스 접근 로그 모니터링
- 비정상적인 접근 패턴 탐지
- 정기적인 보안 업데이트

### 8. 응급 대응

**GitHub에서 민감정보가 노출된 경우:**
1. 즉시 패스워드 변경
2. Git 히스토리 정리
3. 접근 로그 확인
4. 보안 사고 보고

---

## 🔐 체크리스트

- [ ] 데이터베이스 패스워드 변경됨
- [ ] application.properties에서 민감정보 제거됨
- [ ] .gitignore에 보안 파일들 추가됨
- [ ] 환경변수 기반 설정으로 변경됨
- [ ] Git 히스토리에서 민감정보 제거됨
- [ ] 프로덕션 환경 보안 설정 완료됨