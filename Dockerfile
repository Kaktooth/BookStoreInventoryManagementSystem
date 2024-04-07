FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080 9090
COPY "build/libs/BookStoreInventoryManagementSystem-0.0.1-SNAPSHOT.jar" app.jar
ENTRYPOINT ["java","-jar","/app.jar"]