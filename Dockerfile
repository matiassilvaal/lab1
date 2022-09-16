FROM openjdk:17
ARG JAR_FILE=target/lab1.jar
COPY ${JAR_FILE} lab1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/lab1.jar"]