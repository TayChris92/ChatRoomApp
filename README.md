# ChatRoomApp

Requires Java 8 runtime or higher.
Currently only works on localhost.

To run, open a command prompt at project root and use the command "-gradlew run" to launch the server by default.

To run the client, use the command "gradlew run -Dexec.mainClass=ChatClient.controller.ClientMain"

Currently there is a lot more consistent communication between the clients and the server including a broadcasting of user lists which get refreshed upon client termination.
JSONs being sent now include more meaningful data such as color and sender information to each client. 
Chat Bubbles are animated as they are added to the main text area. 

To Come:
Integrating the user database and login functions from FancyFX to allow user data persistence.
Initial screen which allows selection between running server/client.
Design changes.
