package model.JSON;

import java.io.Serializable;

public class MessageCard implements Serializable{

    private String message;
    private String prefix;

    public MessageCard(String message, String prefix) {

        this.message = message;
        this.prefix = prefix;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
