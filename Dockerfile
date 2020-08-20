FROM openjdk:8

WORKDIR /app

COPY target/coletar.jar /app/coletar.jar

ENTRYPOINT ["java", "-jar", "coletar.jar"]