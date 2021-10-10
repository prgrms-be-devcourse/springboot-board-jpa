FROM openjdk:16-jdk-alpine

# myfolder라는 디렉토리 생성 -> 작업 디렉토리로 지정
WORKDIR /myfolder

# 로컬 디렉토리의 jar 파일명을 변수에 저장
ARG JAR_FILE="board-backend-0.0.1-SNAPSHOT.jar"

# jar파일을 컨테이너 내 /myfolder/로 카피
COPY ./build/libs/${JAR_FILE} .

# 컨테이너 내부 jar 파일 실행
ENTRYPOINT ["java", "-jar", "board-backend-0.0.1-SNAPSHOT.jar"]

# 빌드
# docker build . -t spring_8080

# 실행
# -p : 로컬 8080 포트와 컨테이너의 8080 포트를 마운트
# -v : 로컬 /build/libs 경로와 컨테이너의 /myfolder 경로를 마운트
# docker run -d --name con_spring_8080 -p 8080:8080 -v ${PWD}/build/libs/:/myfolder/ spring_8080
