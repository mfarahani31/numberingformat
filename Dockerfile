FROM dev-repository:8083/openjdk:8-jdk-alpine

COPY ./target/numberingformat-*.jar /usr/app/numberingformat.jar

WORKDIR /usr/app

RUN sh -c 'touch numberingformat.jar'

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar $JAVA_OPTS numberingformat.jar