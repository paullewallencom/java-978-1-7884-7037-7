FROM openjdk:8-jre

MAINTAINER tilen.faganel@me.com

RUN mkdir /app

WORKDIR /app

ADD ./api/target/acme-orders-api.jar /app
ADD ./config.yml /app

EXPOSE 9000 9001

CMD ["java", "-jar", "acme-orders-api.jar", "server", "config.yml"]