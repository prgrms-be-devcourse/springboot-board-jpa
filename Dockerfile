FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker build -t hanjo8813/board:spring_img .
# docker push hanjo8813/board:spring_img
# docker run -d --name spring_con -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod" hanjo8813/board:spring_img
# docker run -d --name spring_con -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod" hanjo8813/board:223d567
