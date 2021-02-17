# ChatRoomApp
ChatRoom with group chat, multithreading. Includes custom svg paths and animations for chat bubbles.

I decided to redesign this project. I created a modular structure and I am planning on redesigning the code once again in the future.

This is a Chatroom App which supports group chat. It includes multithreading for the server daemons to support multiple clients.
SVG was used for the chat bubbles.

WELD SE is used for the dependency injection.

Please make sure you have JavaFX runtimes installed https://openjfx.io/

You can run this with the included runnable jars. Run the server app once and as many client instances as you want. It operates on localhost at the moment.

For running through the IDE, import the parent pom into your IDE of choice and build it. It will build both the server and client modules which can then be run separately.

TODO:
My next step will be to clean up the dependencies. Then I will begin on refactoring the code and eventually add new features and work on the UI some more.
