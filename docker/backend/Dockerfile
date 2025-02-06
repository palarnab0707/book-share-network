#---------------------------------Build Stage-----------------------------------#

#Step-1 install the maven tool and open-jdk to build the project and give a tag name as 'build'
FROM maven:3.8.7-openjdk-18 AS BUILD
#Step-2 create a directory where the docker image will be created that is /build
WORKDIR /build
#Step-3 copy the pom.xml inside the current folder that is /build
COPY pom.xml .
#Step-4 this will download the all dependencies from pom.xml
RUN mvn dependency:go-offline
#Step-5 copy the src folder to current folder like /build/src
COPY src ./src
#Step-7 build the application
RUN mvn clean package -DskipTests

#--------------------------------Running Stage---------------------------------#

#Step-1 to run the project is aws we need jdk to to run the project, 17 is our java version we are using in application
FROM amazoncorretto:17
#Step-2 we need to define our arguments for the profile and version, if i dont declare then docker will take
#       by default profile as dev and version as 1.0.0
ARG PROFILE=dev
ARG APP_VERSION=1.0.1
#Step-3 need to create one folder where our application will run
WORKDIR /app
#Step-4 need to copy the .jar file from above build stage which is defined as build, from folder /build/target/ to the /app folder
COPY --from=build /build/target/book-network-*.jar /app/
#Step-5 from the created image we need to run the container in the port 8088(defined in application.yml).
EXPOSE 8088
#Step-6 while executing the application run command docker can't take ARG's directly, so we need to assign
#       ARG's value inside ENV variables. In DB url instead of localhost we need to give container name(get it
#       it from docker compose file) if we are using our db as docker container.
ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
#Step-7 need to give application run command
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar

#Below command is the command for build the image, Need to run it inside our project folder.
#docker build -t bsn/bsn:1.0.0 -f ../../docker/backend/Dockerfile .
#docker build --build-arg="APP_VERSION=1.0.1" -t bsn/bsn:1.0.1 -f ../../docker/backend/Dockerfile .