package com.taydev.chatappclient.Context;

import javax.enterprise.context.ApplicationScoped;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@ApplicationScoped
public class ClientContext {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private String serverIP = "localhost";
    private String name = "Trevor";
    private String colorCode;

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    ClientContext(){}
}
