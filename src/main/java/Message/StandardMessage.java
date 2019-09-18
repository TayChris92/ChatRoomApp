package Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StandardMessage implements Message{

    private String opId = OpId.STANDARD_MESSAGE;
    private String sender;
    private String message;
    private String timeStamp;
    private String colorCode;

    public StandardMessage(){
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public StandardMessage(String sender, String message, String colorCode){
        this.sender = sender;
        this.message = message;
        this.colorCode = colorCode;
        this.timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    @Override
    public String getOpId() {
        return opId;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getSender(){
        return sender;
    }

    public String getMessage(){
        return message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public String getColorCode(){
        return colorCode;
    }
}

