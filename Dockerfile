FROM openjdk:17-jdk-slim

WORKDIR /app

VOLUME /tmp

# Copying the jar file to the container
COPY target/digital-library-management-system-0.0.1-SNAPSHOT.jar app.jar

# exposing the port 8080
EXPOSE 8080

# environment variables for H2
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect

ENTRYPOINT ["java", "-jar", "app.jar"]
