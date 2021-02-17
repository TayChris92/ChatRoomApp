package Message;

public interface Message{

    public static final String MESSAGE_RECEIVED = "RECEIVED";
    public static final String MESSAGE_SENT = "SENT";

    public String getOpId();

    public String getSender();
    public void setSender(String sender);

}
