FROM openjdk:11
COPY . /app
WORKDIR /app
RUN ./mvnw package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/insideTest-0.0.1-SNAPSHOT.jar"]