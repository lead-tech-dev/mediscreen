FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER maximan(ericmaximan@gmail.com)
ADD ./target/clientui-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]