FROM openjdk:17-jdk AS build
WORKDIR /app
COPY pom.xml . 
COPY src src

# Usar Maven normal en lugar del Wrapper si no tienes mvnw
RUN mvn clean package -DskipTests

# Stage 2: Crear la imagen final con OpenJDK 17
FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080