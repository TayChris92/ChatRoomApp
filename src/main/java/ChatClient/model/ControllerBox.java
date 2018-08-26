package ChatClient.model;

import ChatClient.controller.ClientController;
import ChatServer.controller.ServerDaemon;

import java.util.ArrayList;

public class ControllerBox implements ControllerBoxInterface {

    public static ClientController clientController;
    public static ArrayList<ServerDaemon> clientList;

    private ControllerBox(){

    }

    @Override
    public void registerClientController(ClientController clientController){
        this.clientController = clientController;
    }
    
    public void registerClientList(ArrayList<ServerDaemon> clientList){
        this.clientList = clientList;
    }


    public static ControllerBox getInstance(){
        return ControllerBoxHandle.INSTANCE;
    }

    public static class ControllerBoxHandle{
        private static final ControllerBox INSTANCE = new ControllerBox();
    }
    
    


}
