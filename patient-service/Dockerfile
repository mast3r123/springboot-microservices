FROM maven:3.9.5-amazoncorretto-17 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package