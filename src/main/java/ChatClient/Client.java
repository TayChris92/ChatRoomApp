package ChatClient;

import ChatClient.Context.ClientContext;
import ChatClient.controller.ClientUI;
import Decoding.JsonDecoder;
import GUI.ChatBubble;
import Message.*;
import org.jboss.weld.environment.se.WeldContainer;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

@ApplicationScoped
public class Client {

    @Inject
    private ClientUI clientUI;
    private ClientContext context;
    @Inject
    private WeldContainer container;

    public Client(){ }

    public void StartClient() {

        context = container.select(ClientContext.class).get();

        try {
            ConnectToServer();
            setupStreams();
            RequestOnboard();
            messageListener();
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            //CloseConnection();
        }
    }

    public ClientContext getContext(){
        return context;
    }

    private void ConnectToServer() throws IOException {
        clientUI.start();
        clientUI.LookingForConnection();
        boolean looking = true;
        while(context.getConnection() == null){
            try {
                context.setConnection(new Socket(InetAddress.getByName(context.getServerIP()), 6620));
                clientUI.ConnectionFound();
            }catch(ConnectException e) {
                System.out.println("Connect failed, waiting and trying again");
                try {
                    Thread.sleep(2000);//2 seconds
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    private void RequestOnboard(){
        OnboardRequest request = new OnboardRequest();
        sendMessage(request);
    }

    private void setupStreams() throws IOException{
        context.setOutput(new ObjectOutputStream(context.getConnection().getOutputStream()));
        context.getOutput().flush();
        context.setInput(new ObjectInputStream(context.getConnection().getInputStream()));
    }


    private void messageListener() throws IOException {
        while(true){
            try {
                String json = (String) context.getInput().readObject();
                Message message = JsonDecoder.jsonToMessage(json);
                System.out.println("Received:");
                System.out.println(json);

                if (message.getOpId().equals(OpId.ONBOARD)){

                    OnboardInfo onboardInfo = (OnboardInfo) message;
                    context.setColorCode(onboardInfo.getColorCode());
                    List<String> connectedClients = onboardInfo.getConnectedClients();
                    clientUI.UpdateClientList(connectedClients);

                }else if (message.getOpId().equals(OpId.CLIENTLIST)) {
                    ClientList clientList = (ClientList) message;
                    List<String> connectedClients = clientList.getConnectedClients();

                    clientUI.UpdateClientList(connectedClients);
                }else if(message.getOpId().equals(OpId.STANDARD_MESSAGE)){

                    StandardMessage standardMessage = (StandardMessage) message;


                    ChatBubble chatBubble = new ChatBubble(standardMessage, Message.MESSAGE_RECEIVED).getChatBubble();

                    clientUI.UpdateChatWindow(chatBubble);

                }

            }catch(ClassNotFoundException cnf){
            }catch(SocketException se){
                CloseConnection();
            }catch(EOFException eo){

            }
        }
    }

    public void sendMessage(Message message){

        try{
            if(message.getOpId().equals(OpId.STANDARD_MESSAGE)){
                StandardMessage standardMessage = (StandardMessage) message;
                standardMessage.setColorCode(context.getColorCode());
                standardMessage.setSender(context.getName());
                ChatBubble chatBubble = new ChatBubble(standardMessage, Message.MESSAGE_SENT).getChatBubble();
                clientUI.UpdateChatWindow(chatBubble);
                context.getOutput().writeObject(JsonDecoder.messageToJson(standardMessage));
                context.getOutput().flush();
            }else if(message.getOpId().equals(OpId.ONBOARD_REQUEST)){
                OnboardRequest onboardRequest = (OnboardRequest) message;
                onboardRequest.setSender(context.getName());
                context.getOutput().writeObject(JsonDecoder.messageToJson(onboardRequest));
                context.getOutput().flush();
            }
        }catch(Exception io){
            io.printStackTrace();
        }
    }

    public void CloseConnection() throws NullPointerException{

        JSONObject jsonSignOff = new JSONObject();
        jsonSignOff.put("type", "logoff");
        try {
            context.getOutput().writeObject(jsonSignOff.toString());
        } catch (IOException e) {
            //e.printStackTrace();
        }
        try{

            context.getOutput().close();
            context.getInput().close();
            context.getConnection().close();


        }catch(IOException io){
            //io.printStackTrace();
        }

    }

}
