package com.company.views;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static com.company.views.menus.MainMenu.startMenu;


public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startMenu(stage);
    }

    public static Alert getAlert(Stage stage, Alert.AlertType type, String textAlert){
        Alert alert = new Alert(type);
        alert.setHeaderText(textAlert);
        alert.showAndWait();
        return alert;
    }
}