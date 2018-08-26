package ChatClient.controller;

import ChatClient.model.ControllerBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClientController extends Application {

    private Stage primaryStage;
    private static ClientController clientController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chat Room CLIENT");
        showChatWindow();

        clientController.StartClient();
        primaryStage.setOnHidden(event -> {
            clientController.clientHandler.CloseConnection();
            //primaryStage.close();
        });

    }

    public void showChatWindow() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource("ChatRoom/view/ChatRoomTest.fxml"));
            clientController = new ClientController();
            loader.setController(clientController);
            AnchorPane root = loader.load();
            //clientController = new ClientController();



            clientController.SetMainClientController(this);
            ControllerBox.getInstance().registerClientController(clientController);


            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
