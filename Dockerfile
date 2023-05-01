FROM openjdk:8

ENV PATH "$PATH:/static/images"

WORKDIR /app

CMD [ "mkdir static", "cd static", "mkdir images" ]

COPY target/Quiz-App-0.0.1-SNAPSHOT.jar /app/spring-quiz-webservice.jar

ENTRYPOINT [ "java","-jar", "spring-quiz-webservice.jar" ]
