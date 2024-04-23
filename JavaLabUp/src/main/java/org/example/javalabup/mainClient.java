package org.example.javalabup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainClient extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lab-view.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root1, 853, 544);
        stage.setResizable(true);
        stage.setTitle("lab");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}