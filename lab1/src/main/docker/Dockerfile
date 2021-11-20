#FROM bitnami/wildfly:24.0.1-debian-10-r94

#ENV WILDFLY_PASSWORD=password

#COPY ${project.build.finalName}.war /opt/bitnami/wildfly/standalone/deployments

#CMD ["/opt/bitnami/scripts/wildfly/run.sh", "--debug", "*:8787"]
FROM jetty:9.4.44-jdk8

COPY ${project.build.finalName}.war /var/lib/jetty/webapps/ROOT.war

EXPOSE 8787

RUN java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8787 -jar "$JETTY_HOME/start.jar" --add-to-start=webapp,plus,deploy,cdi,debug,logging-logback --approve-all-licenses