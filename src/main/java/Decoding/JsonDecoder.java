package Decoding;

import ChatServer.Context.ServerContext;
import Message.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.IOException;


public class JsonDecoder {

    @Inject
    private ServerContext serverContext;

    public JsonDecoder(){}

    public static String messageToJson(Message message){
        ObjectMapper objectMapper = new ObjectMapper();
        String messageAsJsonString ="";
        try {
            switch(message.getOpId()){
                case(OpId.ONBOARD_REQUEST):
                    OnboardRequest onboardRequest = (OnboardRequest) message;
                    messageAsJsonString = objectMapper.writeValueAsString(onboardRequest);
                    break;
                case(OpId.STANDARD_MESSAGE):
                    StandardMessage standardMessage = (StandardMessage) message;
                    messageAsJsonString = objectMapper.writeValueAsString(standardMessage);
                    break;
                case(OpId.ONBOARD):
                    OnboardInfo onboardInfo = (OnboardInfo) message;
                    messageAsJsonString = objectMapper.writeValueAsString(onboardInfo);
                    break;
                case(OpId.CLIENTLIST):
                    ClientList clientList = (ClientList) message;
                    messageAsJsonString = objectMapper.writeValueAsString(clientList);
                    break;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Message Sent:");
        System.out.println(messageAsJsonString);

        return messageAsJsonString;
    }

    public static Message jsonToMessage(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = null;
        JSONObject jsonObject = new JSONObject(json);
        try {
            switch(jsonObject.getString("opId")){
                case(OpId.ONBOARD_REQUEST):
                    message = (OnboardRequest) objectMapper.readValue(json, OnboardRequest.class);
                    break;
                case(OpId.ONBOARD):
                    message = (OnboardInfo) objectMapper.readValue(json, OnboardInfo.class);
                    break;
                case(OpId.STANDARD_MESSAGE):
                    message = (StandardMessage) objectMapper.readValue(json, StandardMessage.class);
                    break;
                case(OpId.CLIENTLIST):
                    message = (ClientList) objectMapper.readValue(json, ClientList.class);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
