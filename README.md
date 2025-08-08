# Soron Spring Boot Application

## 🚀 프로젝트 개요

Spring Boot 3.5.4 기반의 샘플 CRUD 애플리케이션입니다.

### 기술 스택
- **Framework**: Spring Boot 3.5.4
- **Language**: Java 21
- **Build Tool**: Gradle
- **Database**: MariaDB
- **Cache**: Redis
- **ORM**: MyBatis
- **View**: JSP + JSTL
- **API Documentation**: Swagger/OpenAPI 3

## 🛠️ 개발 환경 설정

### 1. 필수 요구사항
- Java 21+
- MariaDB 10.x+
- Redis 6.x+

### 2. 환경변수 설정

**`.env` 파일 생성 (로컬 개발용):**
```bash
cp .env.example .env
# .env 파일을 편집하여 실제 값 입력
```

**환경변수 목록:**
```bash
DB_URL=jdbc:mariadb://your-db-host:3306/your-database
DB_USERNAME=your-username
DB_PASSWORD=your-password
REDIS_HOST=your-redis-host
REDIS_PORT=6379
SERVER_PORT=9090
SPRING_PROFILES_ACTIVE=local
```

### 3. 로컬 설정 파일

**`src/main/resources/application-local.properties`** 생성:
```properties
# 로컬 개발 설정 (Git에 커밋하지 마세요!)
spring.datasource.url=jdbc:mariadb://localhost:3306/soron
spring.datasource.username=soron
spring.datasource.password=your-password
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

## 🏃‍♂️ 실행 방법

### 로컬 개발 환경
```bash
# Gradle로 실행
./gradlew bootRun --args='--spring.profiles.active=local'

# 또는 JAR 파일 실행
./gradlew build
java -jar build/libs/soron-0.0.1-SNAPSHOT.war --spring.profiles.active=local
```

### 프로덕션 환경
```bash
# 환경변수 설정 후
export SPRING_PROFILES_ACTIVE=prod
export DB_URL="jdbc:mariadb://prod-server:3306/database"
export DB_USERNAME="prod-user"
export DB_PASSWORD="secure-password"

java -jar soron-0.0.1-SNAPSHOT.war
```

## 📡 API 엔드포인트

### 웹 페이지
- **홈페이지**: `http://localhost:9090/`
- **샘플 목록**: `http://localhost:9090/samples`
- **Swagger UI**: `http://localhost:9090/swagger-ui.html`

### REST API
```
GET    /api/samples           - 샘플 목록 조회
GET    /api/samples/{id}      - 샘플 단일 조회
POST   /api/samples           - 샘플 생성
PUT    /api/samples/{id}      - 샘플 수정
DELETE /api/samples/{id}      - 샘플 삭제
```

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/soron/
│   │   ├── common/           # 공통 컴포넌트
│   │   │   ├── config/       # 설정 클래스
│   │   │   ├── exception/    # 예외 처리
│   │   │   ├── response/     # 응답 DTO
│   │   │   └── util/         # 유틸리티
│   │   ├── controller/       # 컨트롤러
│   │   └── sample/           # 샘플 도메인
│   │       ├── controller/   # REST + JSP 컨트롤러
│   │       ├── dto/          # 데이터 전송 객체
│   │       ├── mapper/       # MyBatis 매퍼
│   │       ├── model/        # 도메인 모델
│   │       └── service/      # 비즈니스 로직
│   ├── resources/
│   │   ├── mapper/           # MyBatis XML
│   │   ├── sql/              # DDL 스크립트
│   │   ├── static/           # 정적 리소스
│   │   ├── application*.properties
│   │   └── messages*.properties
│   └── webapp/WEB-INF/views/ # JSP 뷰
└── test/                     # 테스트 코드
```

## 🔐 보안 설정

### 중요 사항
- **절대 실제 패스워드를 코드에 하드코딩하지 마세요**
- 민감한 정보는 환경변수 또는 별도 설정 파일 사용
- `application-local.properties`는 Git에 커밋하지 않음

자세한 보안 가이드는 [SECURITY.md](SECURITY.md)를 참고하세요.

## 🛠️ 개발 가이드

### 새로운 도메인 추가
1. `com.soron.{domain}` 패키지 생성
2. Model, DTO, Mapper, Service, Controller 구현
3. MyBatis XML 매퍼 작성
4. JSP 뷰 페이지 작성

### 코드 스타일
- Lombok 적극 활용
- 일관된 패키지 구조 유지
- 국제화 메시지 사용 (`messages.properties`)
- 커스텀 예외 활용

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 실행
./gradlew test --tests "com.soron.sample.*"
```

## 📚 주요 기능

### ✨ 구현된 기능
- [x] 샘플 CRUD (Create, Read, Update, Delete)
- [x] 페이징 및 검색 기능
- [x] JSP 기반 웹 UI
- [x] REST API + Swagger 문서
- [x] 국제화 지원 (한국어/영어)
- [x] 커스텀 예외 처리
- [x] Enum 기반 상태 관리
- [x] Spring Profiles 지원

### 🔄 향후 개선사항
- [ ] Spring Security 인증/인가
- [ ] 단위 테스트 확장
- [ ] Docker 컨테이너화
- [ ] CI/CD 파이프라인
- [ ] 캐싱 전략 구현

## 🤝 기여하기

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.