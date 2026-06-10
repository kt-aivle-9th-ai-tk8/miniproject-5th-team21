
# 📚 AI 기반 감성 창작 도서 관리 시스템 (Team 21)

> **KT AIVLE School 9th Mini-Project 5th**  
> 본 프로젝트는 사용자가 작성한 글의 제목과 본문 내용을 분석하여  
> OpenAI GPT Image 모델을 통해 세상에 단 하나뿐인 감성 도서 표지를 자동 생성하는 스마트 창작 플랫폼입니다.  
> 이때, *Spring Framework*를 기반으로 한 REST API 서버를 활용하여 도서 데이터의 조회, 생성, 수정, 삭제(CRUD) 기능을 제공합니다.

---

# 📊 데이터 모델 정의

| 필드명             | 데이터 타입 | 설명                  |
| --------------- | ------ | ------------------- |
| `id`            | int    | 고유 식별자              |
| `title`         | string | 도서 제목               |
| `author`        | string | 도서 저자               |
| `content`       | string | 도서 소개 및 본문          |
| `coverImageUrl` | string | Base64 이미지 Data URL |
| `createdAt`     | string | 생성 시각 (ISO 8601)    |
| `updatedAt`     | string | 수정 시각 (ISO 8601)    |

---

# 🔌 API Specification

Base URL:

```text
http://localhost:8080
```

---

## 📝 API 요약

| 기능       | Method | Endpoint     |
| -------- | ------ | ------------ |
| 도서 목록 조회 | GET    | `/books`     |
| 도서 상세 조회 | GET    | `/books/:id` |
| 신규 도서 등록 | POST   | `/books`     |
| 도서 정보 수정 | PATCH  | `/books/:id` |
| 도서 정보 삭제 | DELETE | `/books/:id` |

---

# 🔍 API 요청 / 응답 예시

## 1️⃣ 도서 목록 조회

### Request

```http
GET /books
```

### Response

```json
[
  {
    "id": 1,
    "title": "클린 코드",
    "author": "로버트 C. 마틴",
    "category": "IT/디지털",
    "content": "애자일 소프트웨어 장인 정신 기술 서적...",
    "coverImageUrl": "data:image/png;base64,...",
    "createdAt": "2026-05-26T15:45:00.000Z",
    "updatedAt": "2026-05-26T15:45:00.000Z"
  },
  {
    "id": 2,
    "title": "클린하지 않은 코드",
    "author": "로버트 주니어",
    "category": "IT/디지털",
    "content": "애자일하지 않은 코드도 괜찮다! ...",
    "coverImageUrl": "data:image/png;base64,...",
    "createdAt": "2026-05-26T15:45:00.000Z",
    "updatedAt": "2026-05-26T15:45:00.000Z"
  }
]
```

---

## 2️⃣ 도서 등록

### Request

```http
POST /books
```

### Request Body

```json
{
  "title": "리팩터링 2판",
  "author": "마틴 파울러",
  "category": "IT/디지털",
  "content": "코드를 개선하는 객체지향 기술과 패턴.",
  "coverImageUrl": "data:image/png;base64,...",
  "createdAt": "2026-05-26T15:50:00.000Z",
  "updatedAt": "2026-05-26T15:50:00.000Z"
}
```

---

## 3️⃣ 도서 단건 조회

### Request

```http
GET /books/1
```

### Response

```json
{
    "id": 1,
    "title": "클린 코드",
    "author": "로버트 C. 마틴",
    "category": "IT/디지털",
    "content": "애자일 소프트웨어 장인 정신 기술 서적...",
    "coverImageUrl": "data:image/png;base64,...",
    "createdAt": "2026-05-26T15:45:00.000Z",
    "updatedAt": "2026-05-26T15:45:00.000Z"
}
```

존재하지 않는 ID 요청 시 `404 Not Found` 또는 빈 응답이 반환될 수 있습니다.

---

## 4️⃣ 도서 수정

### Request

```http
PATCH /books/1
```

### Request Body

```json
{
    "id": 1,
    "title": "클린 코드(개정판)",
    "author": "로버트 C. 마틴",
    "category": "IT/디지털",
    "content": "애자일 소프트웨어 장인 정신 기술 서적의 개정판으로...",
    "coverImageUrl": "data:image/png;base64,...",
    "createdAt": "2026-05-26T15:45:00.000Z",
    "updatedAt": "2026-05-28T18:55:00.000Z"
}
```

---

## 5️⃣ 도서 삭제

### Request

```http
DELETE /books/1
```

---

# 👥 Team R&R
## 운영 R&R
- 조장: 박지연
- 서기: 김종현
- 발표자: 김현성

## 기술 R&R
- PM: 김현성, 차태의
  - ERD / API 정의서, README.md 작성, 발표자료 준비, 통합 이슈 추적
- 백엔드 개발(1): 김민우
  - Book Entity 작성, BookRepository, H2 콘솔 확인, Lombok 4종 적용
- 백엔드 개발(2): 오채은
  - BookService 클래스, 비즈니스 로직, BookNotFoundException, `@Transactional`
- 백엔드 개발(3): 윤한아
  - BookController, 5종 CRUD 엔드포인트, `@Valid` + `@NotBlank`, Postman 테스트
- 통합 / 예외처리: 김종현
  - WebConfig(CORS), GlobalExceptionHandler, 풀스택 디버깅, 트러블슈팅 정리
- AI / Frontend 연동: 박지연
  - Frontend 코드 분석, fetch URL 변경 / 1차 연동, OpenAI 표지 흐름, E2E 시연 준비