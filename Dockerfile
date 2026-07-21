FROM eclipse-temurin:21-jdk
WORKDIR /opt
COPY  target/*.jar /opt/app.jar
ENTRYPOINT ["java","-jar","app.jar"]