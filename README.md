# Release Notes App
This project contains the base infrastructure to access the functionalities login and normalize.

The idea of the project was to be the base of the Notes app, and use the normaliser function to filter the one who is the responsible for the note.


The project is in JAVA 8, and uses the springboot in the version 2.7.9.

To build the project, JAVA 8 is required, and Maven in the version 3.9.7.

The command `mvn clean install` will set up the enviroment to build the application.

The file `src/test/resources/script-carga.sql` constains the DATABASE commands to create and add the initial data if needed.

To run as Local profile, the file `src/test/resources/application.properties` has the information to access the `H2` database for development test.


The file `src/test/java/br/com/notesrelease/NotesReleaseApplicationLocal.java` run the application using the `src/test/resources/application.properties` as base configuration file.


The file `src/test/java/br/com/notesrelease/NormaliserTest.java` can be used to test the resolution of Normaliser class.
