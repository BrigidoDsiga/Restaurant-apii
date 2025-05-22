FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o JAR gerado para a imagem
COPY target/*.jar app.jar

# Expõe a porta padrão da aplicação Spring Boot
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]