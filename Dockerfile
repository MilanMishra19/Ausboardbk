
FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
COPY src ./src
RUN chmod +x mvnw 
RUN ./mvnw dependencies:go-offline
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]


