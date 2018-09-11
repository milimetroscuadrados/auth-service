FROM java:8-jre-alpine

RUN apk add --update curl && rm -rf /var/cache/apk/*

ARG JAVA_OPTS

ENV JAVA_OPTS ${JAVA_OPTS}

ADD ./build/libs/oauth-0.0.1-SNAPSHOT.jar /app/oauth.jar

ENTRYPOINT java $JAVA_OPTS -Xmx200m -jar /app/oauth.jar

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:9999/health || exit 1

EXPOSE 9999
