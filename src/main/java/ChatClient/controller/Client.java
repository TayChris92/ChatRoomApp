package ChatClient.controller;

import model.JSONMessageCard;
import model.MessageContainer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import ChatClient.model.ControllerBox;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    public ObjectOutputStream output;
    public ObjectInputStream input;
    private Socket connection;
    private String serverIP;
    private MessageContainer messageBox;
    private String messageDirection;
    public String prefix = "Vanessa";;
    private static JSONObject userCard;
    private boolean listening = true;
    public Color color;
    public Client(){

    }

    public Client(String serverIP) {
        this.serverIP = serverIP;
        userCard = new JSONObject();
        prefix = "Vanessa";
        userCard.put("user", prefix);

    }



    public void StartClient() {

        try {
            ConnectToServer();
            setupStreams();
            messageListener();

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            CloseConnection();
        }
    }

    private void ConnectToServer() throws IOException {

        boolean scanning = true;
        do{
            try{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerBox.clientController.adminText.setText("Looking for server connection.....");
                    }
                });

                connection = new Socket(InetAddress.getByName(serverIP), 6620);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerBox.clientController.adminText.setText("Connected to: " + connection.getInetAddress().getHostAddress());
                        ControllerBox.clientController.textMessageBar.setDisable(false);
                        ControllerBox.clientController.buttonSend.setDisable(false);



                    }
                });

                scanning = false;
            }catch(ConnectException e) {
                System.out.println("Connect failed, waiting and trying again");
                try {
                    Thread.sleep(2000);//2 seconds
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }while (scanning);
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        output.writeObject(userCard.toString());
    }


    private void messageListener() throws IOException {

        do {

            try {
                String incomingCardString = (String) input.readObject();
                JSONObject incomingCardJSON = new JSONObject(incomingCardString);

                if (incomingCardJSON.getString("type").equals("clientlist")) {
                    JSONArray clientlist = new JSONArray(incomingCardJSON.get("clients").toString());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ControllerBox.clientController.listClients.getItems().clear();
                            for(Object o : clientlist){
                                ControllerBox.clientController.listClients.getItems().add(o.toString());
                            }
                        }
                    });


                }else if(incomingCardJSON.get("type").equals("group")){
                    System.out.println("Received" + incomingCardJSON.toString(5));
                    messageDirection = "RECEIVE";
                    messageBox = new MessageContainer(incomingCardJSON,this).MessageContainerBuilder(messageDirection);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ControllerBox.clientController.chatPane.getChildren().add(messageBox);
                            TranslateTransition moveBox = new TranslateTransition(Duration.millis(250), messageBox);
                            messageBox.setTranslateY(messageBox.getLayoutY() - 10);
                            moveBox.setToY(messageBox.getLayoutY());
                            FadeTransition fadeBox = new FadeTransition(Duration.millis(450), messageBox);
                            fadeBox.setFromValue(0);
                            fadeBox.setToValue(1);
                            ParallelTransition boxAnim = new ParallelTransition(moveBox, fadeBox);
                            boxAnim.play();
                        }
                    });
                }else if(incomingCardJSON.get("type").toString().equals("onboarding")){
                    System.out.println(incomingCardString);
                    int rgb = Integer.parseInt(incomingCardJSON.getString("color"));
                    System.out.println(rgb);
                    color = new Color(rgb);
                    System.out.println(color);


                }
                System.out.println("Received" + incomingCardJSON.toString(5));

                }catch(ClassNotFoundException cnf){
                }catch(SocketException se){
                    CloseConnection();
                }catch(EOFException eo){

                }
            } while (listening = true) ;
        }


    public void sendMessage(String message){

        try{

            //MessageCard card = new MessageCard(message, "Vanessa : ");

            JSONObject messageCardJSON = new JSONMessageCard(message,this).standardMessage();
            output.writeObject(messageCardJSON.toString());
            output.flush();

            messageDirection = "SEND";
            messageBox = new MessageContainer(messageCardJSON,this).MessageContainerBuilder(messageDirection);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ControllerBox.clientController.chatPane.getChildren().add(messageBox);
                    TranslateTransition moveBox = new TranslateTransition(Duration.millis(250),messageBox);
                    messageBox.setTranslateY(messageBox.getLayoutY() - 5);
                    moveBox.setToY(messageBox.getLayoutY());
                    FadeTransition fadeBox = new FadeTransition(Duration.millis(500), messageBox);
                    fadeBox.setFromValue(0);
                    fadeBox.setToValue(1);
                    ParallelTransition boxAnim = new ParallelTransition(moveBox,fadeBox);
                    boxAnim.play();
                }
            });



        }catch(Exception io){
            io.printStackTrace();
        }
    }







    public void CloseConnection() throws NullPointerException{

        JSONObject jsonSignOff = new JSONObject();
        jsonSignOff.put("type", "logoff");
        try {
            output.writeObject(jsonSignOff.toString());
        } catch (IOException e) {
            //e.printStackTrace();
        }
        try{
            listening = false;
            output.close();
            input.close();
            connection.close();


        }catch(IOException io){
            //io.printStackTrace();
        }

    }

}
