FROM openjdk:8

ENV PATH "$PATH:/static/images"

WORKDIR /app

CMD [ "mkdir static", "cd static", "mkdir images" ]

COPY target/spring-jenkins-integration.jar /app/spring-jenkins-integration.jar

ENTRYPOINT [ "java","-jar", "spring-jenkins-integration" ]
