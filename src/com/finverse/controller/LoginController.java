package com.finverse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login() {

        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println(email);
        System.out.println(password);

    }
}