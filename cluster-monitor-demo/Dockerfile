FROM eclipse-temurin:21
LABEL authors="Shubham Varshney"
LABEL maintainer="Shubham Varshney"

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY entrypoint.sh entrypoint.sh

ENV PATH=$PATH:${JAVA_HOME}/bin
ENV MAIN_CLASS=com.shubham.Main

RUN echo $MAIN_CLASS


EXPOSE 8009
EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]
