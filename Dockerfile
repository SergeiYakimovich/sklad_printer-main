FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.5

RUN apt-get update && apt-get install -yq make unzip

WORKDIR .

COPY . .

RUN gradle installDist

CMD ./build/install/site.shaerware.store/bin/site.shaerware.store

EXPOSE 8080