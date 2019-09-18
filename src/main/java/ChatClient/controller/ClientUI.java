package ChatClient.controller;


import ChatClient.Client;
import ChatClient.Context.ClientContext;
import GUI.ChatBubble;
import Message.StandardMessage;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ClientUI {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textMessageBar;
    @FXML
    private JFXButton buttonSend;
    @FXML
    private VBox chatPane;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private Label adminText;
    @FXML
    private ListView<String> listClients;

    private Stage primaryStage;
    @Inject
    private Client client;
    @Inject
    private ClientContext context;

    public ClientUI(){ }

    public void UpdateChatWindow(ChatBubble chatBubble){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatPane.getChildren().add(chatBubble);
                TranslateTransition moveBox = new TranslateTransition(Duration.millis(250), chatBubble );
                chatBubble .setTranslateY(chatBubble .getLayoutY() - 10);
                moveBox.setToY(chatBubble .getLayoutY());
                FadeTransition fadeBox = new FadeTransition(Duration.millis(450), chatBubble );
                fadeBox.setFromValue(0);
                fadeBox.setToValue(1);
                ParallelTransition boxAnim = new ParallelTransition(moveBox, fadeBox);
                boxAnim.play();
            }
        });
    }

    public void UpdateClientList(List<String> connectedClients){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listClients.getItems().clear();
                for(String name : connectedClients){
                    listClients.getItems().add(name);
                }
            }
        });
    }

    public void LookingForConnection() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    adminText.setText("Looking for server connection.....");
                }
            });
        }

        public void ConnectionFound(){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    adminText.setText("Connected to: " + client.getContext().getConnection().getInetAddress().getHostAddress());
                    textMessageBar.setDisable(false);
                    buttonSend.setDisable(false);
                }
            });
        }

    public void start() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage = new Stage();
                primaryStage.setTitle("Chat Room CLIENT");

                showChatWindow();
                initialize();

                primaryStage.setOnHidden(event -> {
                    client.CloseConnection();
                });
            }
        });
    }

    public void showChatWindow() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource("ChatRoom/view/fxml/ChatWindow.fxml"));
            loader.setController(this);

            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){

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
                StandardMessage message = new StandardMessage();
                message.setMessage(textMessageBar.getText());
                message.setSender(context.getName());
                textMessageBar.setText("");
                client.sendMessage(message);
            }
        });

        buttonSend.setOnAction(event ->{
            if(!textMessageBar.getText().isEmpty()){
                StandardMessage message = new StandardMessage();
                message.setMessage(textMessageBar.getText());
                message.setSender(context.getName());
                textMessageBar.setText("");
                client.sendMessage(message);
            }
        });
    }

    }