FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/backend.jar
COPY run.sh /app/run.sh
RUN chmod +x /app/run.sh
CMD /app/run.sh