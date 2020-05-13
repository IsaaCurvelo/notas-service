FROM openjdk:11
ADD target/notas-service.jar notas-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notas-service.jar"]