FROM openjdk:8u151-jre-slim

# Copy application jar
COPY target/book-service-*.jar /book-service.jar

# Setup java -jar as entrypoint
ENTRYPOINT ["java", "-jar"]

# Point to application jar
CMD ["/book-service.jar"]
