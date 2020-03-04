FROM tomcat:8.0.20-jre8
COPY /target/intler-iot.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]