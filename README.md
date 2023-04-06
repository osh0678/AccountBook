# AccountBook 프로젝트
> 로그인, 가계부 기능 API 프로젝트입니다.

# 개발 환경
* Java 11
* Spring Boot 2.7.10
* MySQL 5.7

# 프로젝트 구조
```
accountBook/
├── src/     # Spring Boot 서버
│   ├── postman # api 호출 테스트용
│   ├── main
│   │    ├── java # api 소스
│   │    └── resources # DDL, 프론트소스(해당 파일은 사용하려했으나, 사용하지 않음), yml
│   └── test #테스트 코드
│         └──java
│              └── com.seong.accountBook
│                           ├   LoginTest       # 로그인 테스트
│                           └── AccountBookTest # 가계부 기능 테스트
└── README.md
```

# Docker로 실행방법

```
$ cd AccountBook #해당 디렉토리로 이동
$ docker build -t accountbook . #도커 이미지 build
$ docker run -p 8080:80 -d accountbook #도커 실행 (8080이 이미 사용중이라면 다른 포트로 사용바람)
```
# 프로젝트를 진행하면서...
```
새로운 프로젝트를 진행하면서 다시 한 번 개발이 재밌다고 느낀 계기
JAVA와 Spring boot 버전업된 걸 사용해보면서 새로운 걸 또 배운 좋은 기회였다.
validation 부분이라던지 세부적으로 고도화를 못한 부분이 아쉬웠다.
```

# 개발자
* 이름: 오성환
* 이메일 : osh0678@naver.com

