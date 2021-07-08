FROM maven:3.8.1-jdk-11 AS nistagramCampaignMicroserviceTest
ARG STAGE=test
WORKDIR /usr/src/server
COPY . .



FROM maven:3.8.1-jdk-11 AS nistagramCampaignMicroserviceBuild
ARG STAGE=dev
WORKDIR /usr/src/server
# pokusaj da se odvoji dobaljvanje zavisnosti u poseban sloj kako se ne bi dobavljale sve svaki put
# kad se menj samo kod
#COPY pom.xml /tmp/pom.xml
#RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn package -Pdev -DskipTests

FROM openjdk:11.0-jdk as nistagramCampaignMicroserviceRuntime
COPY --from=nistagramCampaignMicroserviceBuild /usr/src/server/target/*.jar nistagram-campaign.jar
CMD java -jar nistagram-campaign.jar


FROM openjdk:11.0-jdk as nistagramCampaignMicroserviceRuntimeDev
COPY ./entrypoint.sh /entrypoint.sh
COPY ./consul-client.json /consul-config/consul-client.json
RUN apt-get install -y \
    curl \
    unzip \
    && curl https://releases.hashicorp.com/consul/1.9.5/consul_1.9.5_linux_amd64.zip -o consul.zip \
    && unzip consul.zip \
    && chmod +x consul \
    && rm -f consul.zip \
    && chmod +x /entrypoint.sh \
    && mkdir consul-data \
    && apt-get remove -y \
    curl \
    unzip

COPY --from=nistagramCampaignMicroserviceBuild /usr/src/server/target/*.jar nistagram-campaign.jar
EXPOSE 8080
CMD ["/entrypoint.sh"]
