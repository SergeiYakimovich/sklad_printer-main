FROM gradle:7.4.0-jdk17

WORKDIR /site.shaerware.store

COPY /site.shaerware.store .

RUN gradle installDist

CMD ./build/install/site.shaerware.store/bin/site.shaerware.store
