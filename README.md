# ChatRoomApp
Decided to revisit this to see how I could apply new things that I've learned and optimize the code.
So far things are going really good.

Both the server and client have a FXMain class which can be run through your IDE of choice.
I'll add instructions for running from the command line later and then finally add some runnable jars to this repo.

I'm not sure if I want to put both the server and client app into their own separate projects. My end goal is to start with a single interface which allows the user to choose if they want to host a server or connect to one. This main interface will be the entry point and will be the only class which extends Application so I don't have a bunch of Application threads running.

Requires Java 8 runtime or higher.
Currently only works on localhost.

<b>Currently Working:</b>

<b>JSON Decoding</b>
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

<b>Dependency Injection</b>
- Weld managed Beans. I find it makes it a lot easier to access server and client thread contexts as well as updating the GUI.

<b>To Come:</b>
- Integrating user database and login functions to allow user data persistence.
- Static Color pool which server manages to prevent unwanted/duplicate colors.
- Initial screen which allows selection between running server/client.
- Design changes.

Uses:
<br>
com.jfoenix:jfoenix:8.0.72<br>
org.json:json:201801302<br>
org.xerial:sqlite-jdbc:3.23.12

