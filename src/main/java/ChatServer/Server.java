package ChatServer;

import ChatServer.Context.ServerContext;
import ChatServer.controller.ServerUI;
import Message.ClientList;
import Message.Message;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Server {

    private ServerContext serverContext;

    @Inject
    private WeldContainer container;

    @Inject
    private ServerUI serverUI;

    public Server(){}

    public void InitializeServer(){
        serverContext = container.select(ServerContext.class).get();

        serverUI.init();
        serverUI.awaitingConnection();

        try {
            serverContext.setServer(new ServerSocket(serverContext.getPort(), 10));
            System.out.println("Server Initialized!");
            System.out.println("Hosting on address: " + serverContext.getServer().getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AwaitConnection(){
        while (true) {
            try{
                Socket clientConnection = serverContext.getServer().accept();
                RemoteClient remoteClient = container.select(RemoteClient.class).get();
                remoteClient.setConnection(clientConnection);
                remoteClient.setDaemon(true);
                remoteClient.start();

                serverContext.getClientList().add(remoteClient);
                serverUI.connectionAccepted();
                serverUI.UpdateClientListBox(serverContext.getClientListNames());

                updateClientLists();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void relayMessage(Message message, RemoteClient daemon){
        serverContext.getClientList().remove(daemon);
        for(RemoteClient e : serverContext.getClientList()){
            e.sendMessage(message);
        }
        serverContext.getClientList().add(daemon);
    }

    public void SendToAll(Message message){
        for(RemoteClient e : serverContext.getClientList()){
            e.sendMessage(message);
        }
    }

    public void updateClientLists(){
        List<String> connectedClients = new ArrayList();

        for(String names : serverContext.getClientListNames()){
            connectedClients.add(names);
        }
        ClientList clientList = new ClientList();
        clientList.setConnectedClients(connectedClients);

        SendToAll(clientList);
    }

    public void CloseConnections(){
        try {
            for(RemoteClient rc : serverContext.getClientList()){
                rc.getContext().getConnection().close();
            }
            serverContext.getServer().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerContext getContext(){
        return serverContext;
    }

}
