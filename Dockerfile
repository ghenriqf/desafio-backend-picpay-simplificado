FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/desafio-backend-picpay-simplificado-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]