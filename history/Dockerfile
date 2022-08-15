FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER maximan(ericmaximan@gmail.com)
ADD ./target/history-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]