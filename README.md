# ChatRoomApp


NOT WORKING until I can figure out dependency issues with JavaFX


Requires Java 8 runtime or higher.
Currently only works on localhost.

To run, open a command prompt at project root and use the command "-gradlew run" to launch the server by default.

To run the client, use the command "gradlew run -Dexec.mainClass=ChatClient.controller.ClientMain"

<b>Currently Working:</b>

<b>Chat bubbles</b>
- Chat bubbles are animated as they are added to the text area.
- Users are assigned color for their chat bubbles by server.

<b>Multithreading</b>
- Clients are each given their own server thread when connected.
- Server provides functions which allow for broadcasting of client data including:
  - Client List
  - Messages
  - Admin commands
 - When terminating, clients send a farewell message to the server to signal proper sendoff.

<b>Messaging</b>
- Messages are sent by JSON which include sender information and message data.
- Messages have a type attribute which flags how they should be handled by the receiver.


<b>To Come:</b>
- Integrating the user database and login functions from FancyFX to allow user data persistence.
- Static Color pool which server manages to prevent unwanted/duplicate colors.
- Initial screen which allows selection between running server/client.
- Design changes.

Uses:
<br>
com.jfoenix:jfoenix:8.0.72<br>
org.json:json:201801302<br>
org.xerial:sqlite-jdbc:3.23.12

