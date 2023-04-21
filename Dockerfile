FROM azul/zulu-openjdk-alpine:17-jre 

WORKDIR /app

COPY target\siakad-presence-automation-0.0.1-SNAPSHOT.jar /app/app.jar

ENV PROFILE=prod

EXPOSE 8080

CMD [ "java","-jar","app.jar" ]