FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY run.sh /app/run.sh
RUN chmod +x /app/run.sh
COPY ${JAR_FILE} /app/backend.jar
CMD /app/run.sh