FROM java:8-jre

MAINTAINER massimo

LABEL description="docker_playtest image"

ADD dist/docker_playtest.tar dist/docker.properties ./

EXPOSE 8080

CMD java -cp "docker_playtest/lib/*" Startup docker.properties