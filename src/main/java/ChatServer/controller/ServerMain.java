package ChatServer.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Caches.ControllerBox;
import model.JSON.JSONMessageCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain extends Application{

    private Stage primaryStage;
    private static ServerController serverController;
    private Socket connection;
    private ServerSocket server;
    public static ArrayList<ServerDaemon> clientList = new ArrayList<>();
    public String prefix = "Taylor";
    private boolean listening = true;
    public Color color = Color.decode("#a5d6a7");


    public ServerMain(){}

    @Override
    public void start(Stage primaryStage) throws IOException {

            this.primaryStage = primaryStage;
            primaryStage.setTitle("Chat Room SERVER");
            showChatWindow();

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InitializeServer();
                        AcceptConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        CloseConnections();
                    }
                }
            });
            mainThread.setDaemon(true);
            mainThread.start();

        primaryStage.setOnHidden(event -> {
            listening=false;
            mainThread.interrupt();
        });

    }

    public void showChatWindow() {

            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ClassLoader.getSystemResource("ChatRoom/view/fxml/ChatWindow.fxml"));

                serverController = new ServerController();
                loader.setController(serverController);
                AnchorPane root = loader.load();
                ControllerBox.getInstance().registerServerController(serverController);
                serverController.setServerMain(this);

                Scene scene = new Scene(root);

                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

        public void InitializeServer(){

            try {
                server = new ServerSocket(6620, 10);
                ControllerBox.getInstance().registerClientList(clientList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    private void AcceptConnection() throws IOException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverController.adminBanner.setAlignment(Pos.CENTER);
                serverController.adminText.setText("Waiting on connection.....");
            }
        });

        while (listening) {

            connection = server.accept();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    serverController.adminText.setText("Hosting on :" + connection.getInetAddress().getHostAddress() + " : " + server.getLocalPort());
                }
            });

            ServerDaemon serverDaemon = new ServerDaemon(connection, this);
            serverDaemon.setDaemon(true);
            clientList.add(serverDaemon);
            serverDaemon.start();
            //clientOnBoard(serverDaemon);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    serverController.textMessageBar.setDisable(false);
                    serverController.buttonSend.setDisable(false);
                }
            });

        }

    }

    public void broadcast(JSONObject messageCardJSON, ServerDaemon daemon){
        clientList.remove(daemon);
        for(ServerDaemon e : clientList){
            e.sendMessage(messageCardJSON);
        }
        clientList.add(daemon);
    }

    public void SendToAll(JSONObject messageCardJSON){
        for(ServerDaemon e : clientList){
            e.sendMessage(messageCardJSON);
        }
    }

    public void updateClientLists(){
        JSONArray clientListArray = new JSONArray();
        clientListArray.put(prefix);

        for(ServerDaemon e : clientList){
            clientListArray.put(e.getPrefix());
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                serverController.listClients.getItems().clear();
                for(Object o : clientListArray){
                    serverController.listClients.getItems().add(o.toString());
                }
            }
        });


        JSONMessageCard clientListJSON = new JSONMessageCard();
        clientListJSON.put("type", "clientlist");
        clientListJSON.put("clients", clientListArray);

        SendToAll(clientListJSON);
    }

    public void CloseConnections(){
        try {
            server.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public JSONArray getClientList(){

        JSONArray clientListArray = new JSONArray();

            clientListArray.put(prefix);

            for(ServerDaemon e : clientList){
                clientListArray.put(e.getPrefix());
            }

        return clientListArray;
        }




}
