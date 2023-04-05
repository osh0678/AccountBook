# 베이스 이미지
FROM adoptopenjdk:11-jre-hotspot

# 작업 폴더 생성
RUN mkdir -p /app

# 작업 폴더 설정
WORKDIR /app

# 애플리케이션 파일 복사
COPY ./target/accountbook-0.0.1-SNAPSHOT.jar /app

# 컨테이너 실행 시 실행할 명령어 설정
CMD ["java", "-jar", "accountbook-0.0.1-SNAPSHOT.jar"]