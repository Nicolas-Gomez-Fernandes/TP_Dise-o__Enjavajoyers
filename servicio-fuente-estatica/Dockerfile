# Usamos Java 17 (versión liviana)
FROM eclipse-temurin:17-jdk-alpine

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos archivos necesarios de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargamos las librerías (para que arranque más rápido después)
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente de tu proyecto
COPY src ./src

# Comando para iniciar la aplicación
CMD ["./mvnw", "spring-boot:run"]
