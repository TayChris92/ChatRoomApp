package model.Caches;

import ChatClient.controller.ClientController;
import ChatServer.controller.ServerController;

public interface ControllerBoxInterface {
    void registerServerController(ServerController serverController);
    void registerClientController(ClientController clientController);
}
