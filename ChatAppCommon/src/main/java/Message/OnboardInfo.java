package Message;

import Util.UtilFunct;

import java.util.List;

public class OnboardInfo implements Message {
    private String opId = OpId.ONBOARD;
    private String colorCode;
    private List<String> connectedClients;
    private String sender;

    public OnboardInfo(){}

    public List<String> getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(List<String> connectedClients) {
        this.connectedClients = connectedClients;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    @Override
    public String getOpId() {
        return opId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getSender() {
        return sender;
    }

    public OnboardInfo(List<String> connectedClients){
        this.connectedClients = connectedClients;
        colorCode = UtilFunct.generateColorCode();
    }

    public String getColorCode(){
        return colorCode;
    }
}
