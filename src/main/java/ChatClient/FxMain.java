package ChatClient;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class FxMain extends Application{

    public void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        Client client = container.select(Client.class).get();

        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
               client.StartClient();
            }
        });
        clientThread.start();


    }


}