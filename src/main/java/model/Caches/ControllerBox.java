package model.Caches;

import ChatClient.controller.ClientController;
import ChatServer.controller.ServerController;
import ChatServer.controller.ServerDaemon;

import java.util.ArrayList;
import java.util.List;

public class ControllerBox implements ControllerBoxInterface{

    public static ServerController serverController;
    public static List<ServerDaemon> clientList = new ArrayList<ServerDaemon>();
    public static ClientController clientController;

    private ControllerBox(){

    }

    @Override
    public void registerServerController(ServerController serverController){
        this.serverController = serverController;
    }

    @Override
    public void registerClientController(ClientController clientController){
        this.clientController = clientController;
    }

    public void registerClientList(List<ServerDaemon> clientList){
        this.clientList = clientList;
    }

    public static ControllerBox getInstance(){
        return ControllerBoxHandle.INSTANCE;
    }

    public static class ControllerBoxHandle{
        private static final ControllerBox INSTANCE = new ControllerBox();
    }


}
