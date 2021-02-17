package Message;

import java.util.List;

public class ClientList implements Message {
    String opId = OpId.CLIENTLIST;
    List<String> connectedClients;
    private String sender;

    public ClientList(){ }

    @Override
    public String getOpId() {
        return opId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(List<String> connectedClients) {
        this.connectedClients = connectedClients;
    }

    @Override
    public String getSender() {
        return null;
    }
}
