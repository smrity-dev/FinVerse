package com.finverse.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println(getClass().getResource("/fxml/Login.fxml"));

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/resources/fxml/Login.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}