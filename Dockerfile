# Usa uma imagem do Maven para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Executa o build do projeto e gera o JAR
RUN mvn clean package -DskipTests

# Usa uma imagem menor do Java para rodar a aplicação
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o JAR gerado para o container final
COPY --from=build /app/target/*.jar app.jar

# Define a porta do container (Render exige a 10000)
EXPOSE 10000

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
