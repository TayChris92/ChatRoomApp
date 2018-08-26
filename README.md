# ChatRoomApp

Requires Java 8 runtime.
Currently only works on localhost.

To run, open a command prompt at project root and use the command "-gradlew run" to launch the server by default.

To run the client, use the command "gradlew run -Dexec.mainClass=ChatClient.controller.MainClientController"

Currently there is a lot more consistent communication between the clients and the server including a broadcasting of user lists which get refreshed upon client termination. We also have more meaningful JSONs being sent which include assigning color information to each client. Chat Bubbles are animated as they are added to the main text area. 

Next up is integrating the User database and login functions from FancyFX. Also adding user inputted ip address and port number. 
