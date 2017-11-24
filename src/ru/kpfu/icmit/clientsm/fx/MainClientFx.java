package ru.kpfu.icmit.clientsm.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClientFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("clientfx.fxml"));
        primaryStage.setTitle("ICMIT messenger");
        primaryStage.setScene(new Scene(root, 510, 425));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
