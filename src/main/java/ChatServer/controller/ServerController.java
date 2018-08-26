/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Caches.ControllerBox;
import model.JSON.JSONMessageCard;
import model.JSON.MessageContainer;
import org.json.JSONObject;

/**
 *
 * @author TC
 */
public class ServerController {

    @FXML
    public TextField textMessageBar;
    @FXML
    private ServerDaemon chatroom;
    @FXML
    private TextArea textArea;
    public ServerDaemon serverDaemon;
    @FXML
    public JFXButton buttonSend;
    private String userID;
    @FXML
    public VBox chatPane;
    @FXML
    public HBox adminBanner;
    @FXML
    public Label adminText;
    private String message;
    @FXML
    public ScrollPane chatScroll;
    @FXML
    JFXButton buttonShowMenu;
    @FXML
    JFXDrawer drawerUsers;
    @FXML
    public ListView<String> listClients;
    public static ServerMain serverMain;
    private String prefix;



    public ServerController(){

    }

    public void setServerMain(ServerMain serverMain){
        this.serverMain = serverMain;
}

    @FXML
    private void initialize(){


        buttonShowMenu.setOnAction(event -> {

                    if (drawerUsers.isClosed()) {
                        drawerUsers.toFront();
                        drawerUsers.open();
                    } else{
                        drawerUsers.close();
                    }










            /* if(vboxMenu.getTranslateX() <= 0){
                TranslateTransition moveMenu = new TranslateTransition(Duration.millis(500), vboxMenu);
                moveMenu.setFromX(vboxMenu.getLayoutX() + 50);
                moveMenu.setToX(vboxMenu.getLayoutX() + 100);
                FadeTransition fadeMenu = new FadeTransition(Duration.millis(500), listUsers);
                fadeMenu.setFromValue(0);
                fadeMenu.setToValue(1);
                ParallelTransition paraMenu = new ParallelTransition(moveMenu,fadeMenu);
                listUsers.setVisible(true);
                paraMenu.play();

                Timeline buttonResize = new Timeline();
                buttonResize.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                                                                new KeyValue(buttonShowMenu.prefWidthProperty(), 30)),
                                                                new KeyFrame(Duration.millis(500),
                                                                        new KeyValue(buttonShowMenu.prefWidthProperty(), 90))

                        );
                buttonResize.play();


            }else{
                TranslateTransition moveMenu = new TranslateTransition(Duration.millis(500), vboxMenu);
                moveMenu.setFromX(vboxMenu.getTranslateX());
                moveMenu.setToX(vboxMenu.getTranslateX() - 50);
                FadeTransition fadeMenu = new FadeTransition(Duration.millis(500), listUsers);
                fadeMenu.setFromValue(1);
                fadeMenu.setToValue(0);
                ParallelTransition paraMenu = new ParallelTransition(moveMenu,fadeMenu);
                listUsers.setVisible(true);
                paraMenu.play();

                Timeline buttonResize = new Timeline();
                buttonResize.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                                new KeyValue(buttonShowMenu.prefWidthProperty(), 90)),
                        new KeyFrame(Duration.millis(400),
                                new KeyValue(buttonShowMenu.prefWidthProperty(), 30))

                );
                buttonResize.play();

            }
           */




        });

        chatPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            chatScroll.layout();
            chatScroll.setVvalue(1.0d);
        });

        chatScroll.addEventFilter(ScrollEvent.ANY, event -> {
            if(event.getDeltaY() >= 0 ){
                chatScroll.setVvalue(chatScroll.getVvalue() - 0.001);
            }else{
                chatScroll.setVvalue(chatScroll.getVvalue() + 0.001);
            }
        });


        textMessageBar.setOnAction(event -> {

            if(!textMessageBar.getText().isEmpty()){
                message = textMessageBar.getText();
                textMessageBar.setText("");

                prefix = "Taylor";

                JSONObject messageCardJSON = new JSONMessageCard(message,serverMain).standardMessage();

                serverMain.SendToAll(messageCardJSON);

                String messageDirection = "SEND";
                MessageContainer messageBox = new MessageContainer(messageCardJSON).MessageContainerBuilder(messageDirection);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerBox.serverController.chatPane.getChildren().add(messageBox);
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

            }


        });

        buttonSend.setOnAction(event -> {

            if(!textMessageBar.getText().isEmpty()){
                message = textMessageBar.getText();
                textMessageBar.setText("");

                prefix = "Taylor";

                JSONObject messageCardJSON = new JSONMessageCard(message,serverMain).standardMessage();

                serverMain.SendToAll(messageCardJSON);

                String messageDirection = "SEND";
                MessageContainer messageBox = new MessageContainer(messageCardJSON).MessageContainerBuilder(messageDirection);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerBox.serverController.chatPane.getChildren().add(messageBox);
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

            }

        });


    }

}


