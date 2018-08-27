/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import model.Caches.ControllerBox;
import model.JSON.JSONMessageCard;
import model.JSON.MessageContainer;
import model.Util.UtilFunct;
import org.json.JSONObject;

import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerDaemon extends Thread{

    public ObjectOutputStream output;
    public ObjectInputStream input;
    private MessageContainer messageBox;
    private String messageDirection;
    public String prefix;
    private JSONObject userCard;
    private ServerMain mainServer;
    boolean listening = true;
    private Socket connection;
    public Color color;


    public ServerDaemon(Socket connection, ServerMain mainServer) {
        this.connection = connection;
        this.mainServer = mainServer;

    }

    private void StreamSetup() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void CreateClientProfile(){
        try {
            String userCardString = (String) input.readObject();
            userCard = new JSONObject(userCardString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        prefix = userCard.getString("user");
        //System.out.println("Prefix: " + prefix);
        //mainServer.clientList.add(this);

        clientOnBoard();
        mainServer.updateClientLists();



        //clientList.add(this);

        }


    private void messageListener() throws IOException {

        do{

            try{

                String incomingCardString = (String)input.readObject();
                JSONObject incomingCardJSON = new JSONObject(incomingCardString);

                if(incomingCardJSON.get("type").toString().equals("logoff")){
                    ServerMain.clientList.remove(this);
                   mainServer.updateClientLists();

                }else if(incomingCardJSON.get("type").toString().equals("group")){

                    System.out.println("Received from " + incomingCardJSON.get("prefix") + "\n" + incomingCardJSON.toString(5));

                    messageDirection = "RECEIVE";
                    messageBox = new MessageContainer(incomingCardJSON).MessageContainerBuilder(messageDirection);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ControllerBox.serverController.chatPane.getChildren().add(messageBox);
                            TranslateTransition moveBox = new TranslateTransition(Duration.millis(250),messageBox);
                            messageBox.setTranslateY(messageBox.getLayoutY() - 10);
                            moveBox.setToY(messageBox.getLayoutY());
                            FadeTransition fadeBox = new FadeTransition(Duration.millis(450), messageBox);
                            fadeBox.setFromValue(0);
                            fadeBox.setToValue(1);
                            ParallelTransition boxAnim = new ParallelTransition(moveBox,fadeBox);
                            boxAnim.play();

                        }
                    });

                    mainServer.broadcast(incomingCardJSON,this);

                }


            }catch(ClassNotFoundException cnf) {
            }catch(SocketException se){
                CloseConnection();

            }catch(EOFException eo){

            }
        }while (listening = true);
    }

    public void sendMessage(JSONObject messageCardJSON){

        try{

            output.writeObject(messageCardJSON.toString());
            output.flush();


        }catch(Exception io){
            io.printStackTrace();
        }


    }

    public void CloseConnection() {

            try{
                listening = false;
                connection.close();
                output.close();
                input.close();
                //server.close();

            }catch(IOException io){
                io.printStackTrace();
            }catch(NullPointerException np){
                System.out.println("No Connection Established!");

            }

    }
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void run(){

        try {
            StreamSetup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateClientProfile();
        try {
            messageListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CloseConnection();
    }

    public void clientOnBoard(){

        UtilFunct utilDaemon = new UtilFunct();
        color = utilDaemon.generateColor();

        JSONObject clientOnBoard = new JSONMessageCard().onboardingMessage(mainServer);
        sendMessage(clientOnBoard);

    }

}


