package ChatClient.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Caches.ControllerBox;

public class ClientMain extends Application {

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

        });

    }

    public void showChatWindow() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource("ChatRoom/view/fxml/ChatWindow.fxml"));
            clientController = new ClientController();
            loader.setController(clientController);
            AnchorPane root = loader.load();


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
