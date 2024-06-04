FROM openjdk:19

RUN microdnf install findutils

WORKDIR .

COPY . .

RUN ./gradlew installDist

CMD ./build/install/site.shaerware.store/bin/site.shaerware.store

EXPOSE 8080