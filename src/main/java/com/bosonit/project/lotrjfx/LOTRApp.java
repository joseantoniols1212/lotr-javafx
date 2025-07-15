package com.bosonit.project.lotrjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LOTRApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LOTRApp.class.getResource("LOTR-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("LOTR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}