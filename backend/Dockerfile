# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash

# Copy WAR
COPY build/libs/postal-code-service.war /app.war

# runs application
CMD ["/usr/bin/java", "-jar", "/app.war"]