# Backend

This project was made in IntelliJ on Ubuntu 20.04, using spring-boot framework with maven.

This is the preffered IDE on Ubuntu 20.04, since it comes with both Spring and Maven:

> IntelliJ https://linuxhint.com/install-intellij-idea-on-ubuntu-20-04/
> 
> install https://ubuntuhandbook.org/index.php/2021/03/oracle-java-16-released-install-ubuntu-20-04/
> 
> set the JAVA_HOME variable https://vitux.com/how-to-setup-java_home-path-in-ubuntu/

These libraries should be installed and mannually added to IntelliJ (link the .JAR files)

> JEP https://github.com/ninia/jep

> Colt https://dst.lbl.gov/ACSSoftware/colt/i

The links to the aforementioned interpreter .py files in JEP calls (see ExpressionController in package Controllers)
HAVE to be reassigned manually for the application (or tests) to run.

Main SpringBoot backend app, the running of which runs the backend
> backend/src/main/java/application/SpringbootBackendApplication.java

Database used:
MySQL
> For using it with the local database, change variables in application.properties
