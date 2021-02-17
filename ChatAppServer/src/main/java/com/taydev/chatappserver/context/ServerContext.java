package com.taydev.chatappserver.context;

import com.taydev.chatappserver.RemoteClient;
import com.taydev.chatappserver.controller.ServerUI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServerContext {

    private String name = "Server";
    private String colorCode = "-3219810";
    private Socket clientConnection;
    private ServerSocket server;
    private int port = 6620;
    private ArrayList<RemoteClient> clientList = new ArrayList<>();
    @Inject
    private ServerUI serverUI;

    public ServerUI getMainUI(){
        return serverUI;
    }

    public List<String> getClientListNames(){
        List<String> clientListNames = new ArrayList<>();
        clientListNames.add(name);
        for(RemoteClient rc : clientList){
            clientListNames.add(rc.getContext().getName() + ":" + rc.getName());
        }
        return clientListNames;
    }
    public String getName() {
        return name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public Socket getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(Socket clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ArrayList<RemoteClient> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<RemoteClient> clientList) {
        this.clientList = clientList;
    }



}
