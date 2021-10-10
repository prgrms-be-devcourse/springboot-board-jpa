FROM openjdk:16-jdk-alpine

# 로컬 디렉토리의 jar파일경로를 변수에 저장
ARG JAR_FILE=build/libs/*.jar

# 저장한 경로의 jar 파일을 -> 컨테이너 내 app.jar로 카피
COPY ${JAR_FILE} app.jar

# jdbc 난수 설정을 제거하고 컨테이너 내부 jar 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 빌드
# docker build . -t spring_8080

# 실행
# docker run -d --name con_spring_8080 -p 8080:8080 spring_8080