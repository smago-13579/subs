FROM openjdk

MAINTAINER Sultan Alibekov <sultan-13579@yandex.ru>

EXPOSE 8080

ENV PROJECT_NAME subscription.jar

COPY ./target/${PROJECT_NAME} /app/

ENTRYPOINT ["java","-jar","/app/subscription.jar"]