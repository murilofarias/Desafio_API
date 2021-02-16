FROM openjdk:8


WORKDIR /opt/spring_boot

COPY /target/Desafio-API-0.0.1-SNAPSHOT.jar spring_boot_com_mysql.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8008

CMD java -jar spring_boot_com_mysql.jar