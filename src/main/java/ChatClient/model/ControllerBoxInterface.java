package ChatClient.model;

import ChatClient.controller.ClientController;
import ChatServer.controller.ServerDaemon;

import java.util.ArrayList;

public interface ControllerBoxInterface {
    void registerClientController(ClientController clientController);
    void registerClientList(ArrayList<ServerDaemon> clientList);

}
