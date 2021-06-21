FROM maven:3.8.1-jdk-11 AS nistagramCampaignMicroserviceTest
ARG STAGE=test
WORKDIR /usr/src/server
COPY . .

FROM maven:3.8.1-jdk-11  AS nistagramCampaignMicroserviceBuild
ARG STAGE=dev
WORKDIR /usr/src/server
COPY . .
RUN mvn package -Pdev -DskipTests

FROM openjdk:11.0-jdk as nistagramCampaignMicroserviceRuntime
COPY --from=nistagramCampaignMicroserviceBuild /usr/src/server/target/*.jar nistagram-campaign.jar
CMD java -jar nistagram-campaign.jar
