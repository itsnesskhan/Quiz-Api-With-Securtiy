FROM openjdk:8

WORKDIR /app

COPY target/Quiz-App-0.0.1-SNAPSHOT.jar /app/spring-quiz-webservice.jar

ENTRYPOINT [ "java","-jar", "spring-quiz-webservice.jar" ]
