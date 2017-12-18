package ru.kpfu.icmit.clientsm.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.icmit.clientsm.Messages;
import ru.kpfu.icmit.clientsm.StoreMessages;

public class MainClientFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("clientfx.fxml"));
        primaryStage.setTitle("ICMIT messenger");
        primaryStage.setScene(new Scene(root, 510, 425));
        primaryStage.show();
        //loader.getController();
    }

    @Override
    public void stop () {
        StoreMessages.writeMessages(Messages.messages, "history.joc");

    }
    public static void main(String[] args) { launch(args); }
}
