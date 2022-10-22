FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend.jar
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
ENTRYPOINT ["java","-jar","/backend.jar"]