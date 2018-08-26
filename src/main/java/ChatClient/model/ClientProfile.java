package ChatRoom.ChatClient.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientProfile {

    private Socket connection;
    private String prefix;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientProfile(String prefix, Socket connection) {
        this.connection = connection;
        this.setPrefix(prefix);

        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ClientProfile(){

    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }



    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
