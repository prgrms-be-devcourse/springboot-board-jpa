FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker build -t hanjo8813/board:spring_img .
# docker push hanjo8813/board:spring_img
# docker run --name spring_con -p 8081:8081 -e "SPRING_PROFILES_ACTIVE=local" hanjo8813/board:spring_img