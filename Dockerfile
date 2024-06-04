FROM gradle:7.4.0-jdk17

WORKDIR /sklad_printer-main

COPY /sklad_printer-main .

RUN gradle installDist

CMD ./build/install/site.shaerware.store/bin/site.shaerware.store
