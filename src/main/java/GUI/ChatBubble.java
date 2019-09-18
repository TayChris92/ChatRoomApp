package GUI;

import Message.Message;
import Message.StandardMessage;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;

public class ChatBubble extends HBox {

    private TextField text;
    private Text sender = new Text();
    private Text messageText = new Text();
    private TextFlow textFlow;
    private StandardMessage message;
    private Text timeStamp = new Text();
    private String colorCode;
    private String direction;

    public ChatBubble(StandardMessage message, String direction){
        this.message = message;
        this.direction = direction;
    }

    public ChatBubble getChatBubble(){

        textFlow = new TextFlow();
        messageText.setText(message.getMessage());

        sender.setText(message.getSender() + ": ");
        sender.setStyle("-fx-font-weight: bold;");

        timeStamp.setText("\n \t \t \t \t" + message.getTimeStamp());
        timeStamp.setStyle("-fx-font-size: 6pt");

        textFlow.getChildren().addAll(sender,messageText,timeStamp);

        if(direction == Message.MESSAGE_SENT) {
            super.setAlignment(Pos.CENTER_RIGHT);
        }else if(direction == Message.MESSAGE_RECEIVED){
            super.setAlignment(Pos.CENTER_LEFT);
            textFlow.getStyleClass().add("messageincoming");
        }

        Color color = new Color(Integer.valueOf(message.getColorCode()));
        String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));

        textFlow.getStylesheets().add(ClassLoader.getSystemResource("ChatRoom/view/css/textflow.css").toExternalForm());
        textFlow.setStyle("-fx-background-color: " + hexColor);
        super.getChildren().add(textFlow);
        setVisible(true);

        return this;
    }


}
