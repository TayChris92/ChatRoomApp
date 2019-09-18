# ChatRoomApp
Decided to revisit this to see how I could apply new things that I've learned and optimize the code.
So far things are going really good.

Both the server and client have a FXMain class which can be run through your IDE of choice.
I'll add instructions for running from the command line later and then finally add some runnable jars to this repo.

Requires Java 8 runtime or higher.
Currently only works on localhost.

<b>Currently Working:</b>

<b>JSON Decoding>
-Allows proper json to object mapping for sent and received messages.
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

