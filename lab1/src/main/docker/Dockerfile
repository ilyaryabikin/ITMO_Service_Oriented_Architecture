FROM jetty:9.4.44-jdk8-slim

COPY ${project.build.finalName}.war /var/lib/jetty/webapps/ROOT.war

EXPOSE 8787

RUN java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8787 -jar "$JETTY_HOME/start.jar" --add-to-start=webapp,plus,deploy,cdi,logging-logback,debug,jmx --approve-all-licenses