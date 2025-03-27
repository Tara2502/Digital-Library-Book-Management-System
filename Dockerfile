# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy Maven-related files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Grant execution permissions for Maven wrapper
RUN chmod +x mvnw

# Copy source code
COPY src ./src

# Build the application inside the container
RUN ./mvnw clean package -DskipTests

# Move to a minimal JDK image to reduce size
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy only the built JAR file from the previous stage
COPY --from=0 /app/target/digital-library-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Set environment variables for H2
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
