package com.finverse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    public void logout(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(
                getClass().getResource("/resources/fxml/Login.fxml")
        );

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("FinVerse Login");
        stage.show();
    }
}