package com.taydev.chatappserver.controller;

import GUI.ChatBubble;
import Message.Message;
import Message.StandardMessage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.taydev.chatappserver.Server;
import com.taydev.chatappserver.context.ServerContext;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jboss.weld.environment.se.WeldContainer;

@ApplicationScoped
public class ServerUI {

    @FXML
    private TextField textMessageBar;
    @FXML
    private TextArea textArea;
    @FXML
    private JFXButton buttonSend;
    @FXML
    private VBox chatPane;
    @FXML
    private HBox adminBanner;
    @FXML
    private Label adminText;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private JFXButton buttonShowMenu;
    @FXML
    private JFXDrawer drawerUsers;
    @FXML
    private ListView<String> listClients;
    @Inject
    private ServerContext serverContext;
    @Inject
    private Server mainServer;
    @Inject
    private WeldContainer container;

    private Stage primaryStage;

    public ServerUI() {
    }

    public void awaitingConnection() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                adminBanner.setAlignment(Pos.CENTER);
                adminText.setText("Waiting on connection.....");
            }
        });
    }

    public void init() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage = new Stage();
                primaryStage.setTitle("Chat Room SERVER");

                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent t) {
                        Platform.exit();
                        System.exit(0);
                    }
                });

                showChatWindow();
            }
        });

    }

    public void connectionAccepted() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                adminText.setText("Hosting on :" + serverContext.getServer().getInetAddress().getHostAddress() + " : " + serverContext.getServer().getLocalPort());
                textMessageBar.setDisable(false);
                buttonSend.setDisable(false);
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
            //ControllerBox.getInstance().registerServerController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        buttonShowMenu.setOnAction(event -> {
            if (drawerUsers.isClosed()) {
                drawerUsers.toFront();
                drawerUsers.open();
            } else {
                drawerUsers.close();
            }
        });

        chatPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            chatScroll.layout();
            chatScroll.setVvalue(1.0d);
        });

        chatScroll.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() >= 0) {
                chatScroll.setVvalue(chatScroll.getVvalue() - 0.001);
            } else {
                chatScroll.setVvalue(chatScroll.getVvalue() + 0.001);
            }
            if (!textMessageBar.getText().isEmpty()) {
                String message = textMessageBar.getText();
                textMessageBar.setText("");

                StandardMessage outgoingMessage = new StandardMessage();
                outgoingMessage.setColorCode(serverContext.getColorCode());
                outgoingMessage.setMessage(message);
                outgoingMessage.setSender(serverContext.getName());

                mainServer.SendToAll(outgoingMessage);

                String messageDirection = "SEND";
                ChatBubble messageBox = new ChatBubble(outgoingMessage, messageDirection).getChatBubble();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        chatPane.getChildren().add(messageBox);
                        TranslateTransition moveBox = new TranslateTransition(Duration.millis(250), messageBox);
                        messageBox.setTranslateY(messageBox.getLayoutY() - 5);
                        moveBox.setToY(messageBox.getLayoutY());
                        FadeTransition fadeBox = new FadeTransition(Duration.millis(500), messageBox);
                        fadeBox.setFromValue(0);
                        fadeBox.setToValue(1);
                        ParallelTransition boxAnim = new ParallelTransition(moveBox, fadeBox);
                        boxAnim.play();
                    }
                });
            }
        });

        buttonSend.setOnAction(event -> {

            if (!textMessageBar.getText().isEmpty()) {
                StandardMessage message = new StandardMessage();
                message.setMessage(textMessageBar.getText());
                message.setSender(serverContext.getName());
                message.setColorCode(serverContext.getColorCode());

                mainServer.SendToAll(message);
                updateChatWindow(message, Message.MESSAGE_SENT);
                textMessageBar.setText("");
            }
        });

        textMessageBar.setOnAction(event -> {
            if (!textMessageBar.getText().isEmpty()) {
                StandardMessage message = new StandardMessage();
                message.setMessage(textMessageBar.getText());
                message.setSender(serverContext.getName());
                message.setColorCode(serverContext.getColorCode());

                mainServer.SendToAll(message);
                updateChatWindow(message, Message.MESSAGE_SENT);
                textMessageBar.setText("");
            }
        });
    }

    public void updateChatWindow(StandardMessage message, String direction) {

        ChatBubble chatBubble = new ChatBubble(message, direction).getChatBubble();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatPane.getChildren().add(chatBubble);
                TranslateTransition moveBox = new TranslateTransition(Duration.millis(250), chatBubble);
                chatBubble.setTranslateY(chatBubble.getLayoutY() - 10);
                moveBox.setToY(chatBubble.getLayoutY());
                FadeTransition fadeBox = new FadeTransition(Duration.millis(450), chatBubble);
                fadeBox.setFromValue(0);
                fadeBox.setToValue(1);
                ParallelTransition boxAnim = new ParallelTransition(moveBox, fadeBox);
                boxAnim.play();
            }
        });
    }

    public void UpdateClientListBox(List<String> clientList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                listClients.getItems().clear();
                for (Object o : clientList) {
                    listClients.getItems().add(o.toString());
                }
            }
        });
    }
}
