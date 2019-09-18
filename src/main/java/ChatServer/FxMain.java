package ChatServer;

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

        Server server = container.select(Server.class).get();

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.InitializeServer();
                server.AwaitConnection();
            }
        });
        serverThread.start();


    }


}
