/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taydev.chatappserver;

import com.taydev.chatappserver.context.RemoteClientContext;
import com.taydev.chatappserver.context.ServerContext;
import com.taydev.chatappserver.controller.ServerUI;
import Decoding.JsonDecoder;
import Message.Message;
import Message.OnboardInfo;
import Message.OpId;
import Message.StandardMessage;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Inject;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Message.*;

public class RemoteClient extends Thread{

    @Inject
    private ServerContext serverContext;
    @Inject
    private Server mainServer;
    @Inject
    private ServerUI serverUI;
    @Inject
    private RemoteClientContext context;
    @Inject
    private WeldContainer container;

    public RemoteClient(){}

    @Override
    public void run(){
        StreamSetup();
        try {
            messageListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CloseConnection();
    }

    private void init(){

    }
    public void setConnection(Socket connection){
        context.setConnection(connection);
    }

    private void StreamSetup(){
        try{
            context.setOutput(new ObjectOutputStream(context.getConnection().getOutputStream()));
            context.getOutput().flush();
            context.setInput(new ObjectInputStream(context.getConnection().getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendOnboardInfo(){
        OnboardInfo clientInfo = new OnboardInfo(mainServer.getContext().getClientListNames());
        sendMessage(clientInfo);
    }

    private void messageListener() throws IOException {
        do{
            try {
                String incomingJson = (String) context.getInput().readObject();

                JsonDecoder decoder = container.select(JsonDecoder.class).get();
                Message message = decoder.jsonToMessage(incomingJson);
                System.out.println("Received from: " + message.getSender());
                System.out.println(incomingJson);

                if (message instanceof StandardMessage) {
                    mainServer.getContext().getMainUI().updateChatWindow((StandardMessage) message, Message.MESSAGE_RECEIVED);
                    mainServer.relayMessage(message, this);
                }else if (message instanceof OnboardRequest){
                    sendOnboardInfo();
                    context.setName(message.getSender());
                }

            }catch (EOFException | ClassNotFoundException e){
                e.printStackTrace();
                CloseConnection();
            }

        }while (true);

    }

    public void sendMessage(Message message){
        message.setSender(serverContext.getName());
        String outgoing = JsonDecoder.messageToJson(message);
        try{
            context.getOutput().writeObject(outgoing);
            context.getOutput().flush();
        }catch(Exception io){
            io.printStackTrace();
        }
    }

    public void CloseConnection() {
        try{
            context.getConnection().close();
            context.getOutput().close();
            context.getInput().close();
        }catch(IOException io){
            io.printStackTrace();
        }catch(NullPointerException np){
            System.out.println("No Connection Established!");
        }
    }

    public RemoteClientContext getContext(){
        return context;
    }

}


