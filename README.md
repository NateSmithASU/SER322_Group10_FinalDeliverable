RUNNING THIS PROGRAM
This program was written on Java 8 and runs in the Java 8 JRE out of the box:

1. Open a console at the folder /dist/
2. Invoke the file Resource_Management_App.jar using the command <code>java -jar ResourceManagementApp.jar</code>

The login window should appear, prepopulated with login credentials for the MySQL server holding the data, which is hosted by Amazon RDS. You may need to configure your Firewall to allow this connection.

Alternatively, the program can be rebuilt in the IDE of your choice by importing the contents of the /src/ folder into a new project. The MySQL JDBC and potentially the JavaFX libraries will need to be included in your project as libraries, depending on which version of the JDK you are running.
