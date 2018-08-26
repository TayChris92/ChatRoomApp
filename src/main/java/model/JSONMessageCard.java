package model;

import ChatClient.controller.Client;
import ChatServer.controller.ServerDaemon;
import ChatServer.controller.ServerMain;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONMessageCard extends JSONObject implements Serializable {

    private String message;
    private String prefix;
    private String timeStamp;
    private Color color;
    public static ServerMain mainServer;


    public JSONMessageCard(){

    }

    public JSONMessageCard(String message, String prefix) {

        this.message = message;
        this.prefix = prefix;
        timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
    }

    public JSONMessageCard(String message, ServerMain mainServer) {
        this.mainServer = mainServer;
        this.message = message;
        this.prefix = mainServer.prefix;
        timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
        color = mainServer.color;
    }

    public JSONMessageCard(String message, ServerDaemon e) {

        this.message = message;
        this.prefix = e.prefix;
        color = e.color;
        timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
    }

    public JSONMessageCard(String message, Client client) {

        this.message = message;
        this.prefix = client.prefix;
        color = client.color;
        timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
    }

    public JSONObject standardMessage(){

        put("type", "group");
        put("message", message);
        put("prefix", prefix);
        put("timeStamp", timeStamp);
        put("color", String.valueOf(color.getRGB()));

        return this;
    }

    public JSONObject onboardingMessage(ServerMain mainServer){

        JSONArray clientListArray = mainServer.getClientList();

        put("type", "onboarding");
        put("clients", clientListArray);
        put("color", String.valueOf(mainServer.generateColor().getRGB()));

        return this;
    }



}
