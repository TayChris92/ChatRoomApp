package model.JSON;
import ChatClient.controller.Client;
import ChatServer.controller.ServerDaemon;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.JSONObject;

import java.awt.*;

public class MessageContainer extends HBox {

    private TextField text;
    private Text prefix = new Text();
    private Text message = new Text();
    private TextFlow textFlow;
    private MessageCard card;
    private JSONObject messageCardJSON;
    private Text timeStamp = new Text();

    public MessageContainer(Color color){
        
    }

    public MessageContainer(JSONObject messageCardJSON) {
        this.messageCardJSON = messageCardJSON;
    }
    public MessageContainer(JSONObject messageCardJSON, Client client) {
        this.messageCardJSON = messageCardJSON;
    }
    public MessageContainer(JSONObject messageCardJSON, ServerDaemon serverDaemon) {
        this.messageCardJSON = messageCardJSON;
    }

    public MessageContainer MessageContainerBuilder(String messageDirection){

        textFlow = new TextFlow();

        message.setText(messageCardJSON.getString("message"));
        prefix.setText(messageCardJSON.getString("prefix")+ ": ");
        prefix.setStyle("-fx-font-weight: bold;");
        timeStamp.setText("\n \t \t \t \t" + messageCardJSON.getString("timeStamp"));
        timeStamp.setStyle("-fx-font-size: 6pt");

        textFlow.getChildren().addAll(prefix,message,timeStamp);

        if(messageDirection == "SEND") {

            super.setAlignment(Pos.CENTER_RIGHT);

        }else if(messageDirection == "RECEIVE"){

            super.setAlignment(Pos.CENTER_LEFT);
            textFlow.getStyleClass().add("messageincoming");

        }

        Color color = new Color(Integer.parseInt(messageCardJSON.getString("color")));
        String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));

        textFlow.getStylesheets().add(ClassLoader.getSystemResource("ChatRoom/view/css/textflow.css").toExternalForm());
        textFlow.setStyle("-fx-background-color: " + hexColor);
        super.getChildren().add(textFlow);

        return this;
    }


}
