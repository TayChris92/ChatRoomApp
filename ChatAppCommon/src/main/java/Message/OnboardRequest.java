package Message;

public class OnboardRequest implements Message {
    private String opId= OpId.ONBOARD_REQUEST;
    private String sender;

    @Override
    public String getOpId() {
        return opId;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    @Override
    public String getSender() {
        return sender;
    }
}
