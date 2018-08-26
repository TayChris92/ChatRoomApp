package ChatClient.controller;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

public class ClientController {

    @FXML
    private TextArea textArea;

    @FXML
    public TextField textMessageBar;

    @FXML
    public JFXButton buttonSend;
    private ClientMain clientMain;
    public Client clientHandler = new Client("localhost");
    private String userID;
    private String message;
    @FXML
    public VBox chatPane;
    @FXML
    public ScrollPane chatScroll;
    @FXML
    public Label adminText;
    @FXML
    public ListView<String> listClients;

    public ClientController(){
            userID = "Client";
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
                message = textMessageBar.getText();
                textMessageBar.setText("");
                clientHandler.sendMessage(message);
            }

        });

        buttonSend.setOnAction(event ->{

            if(!textMessageBar.getText().isEmpty()){
                message = textMessageBar.getText();
                textMessageBar.setText("");
                clientHandler.sendMessage(message);
            }



        });
    }

    public void SetMainClientController(ClientMain clientMain){
        this.clientMain = clientMain;
    }

    public void StartClient(){

        Thread client = new Thread(()->{
            clientHandler.StartClient();
        });

        client.setDaemon(true);
        client.start();


    }


    }