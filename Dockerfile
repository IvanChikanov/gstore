FROM amazoncorretto:17-alpine3.20-jdk
ARG JAR_FILE=gstore.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
ENV SQL_HOST=bot_db
ENV DB_PORT=
ENV DB_NAME=bot
ENV DB_USER=spring
ENV DB_PASS=postgres