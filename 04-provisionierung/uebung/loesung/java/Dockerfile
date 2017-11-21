FROM alpine
MAINTAINER Simon Baeumler, simon.baeumler@qaware.de
RUN apk update
RUN apk add openjdk8-jre
RUN mkdir -p /opt/app/
ADD  ccexercise-0.0.1-SNAPSHOT.jar /opt/app/ccexercise-0.0.1-SNAPSHOT.jar
ENTRYPOINT exec java -jar /opt/app/ccexercise-0.0.1-SNAPSHOT.jar
EXPOSE 8080