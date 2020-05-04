package com.company.views.error;

import com.company.string.StringFile;
import com.company.views.menus.AdminMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.function.Function;

public class ErrorMessage {

    public void getErrorMessage(Stage stage){
        Label error = new Label(StringFile.ERROR);
        Button goBack = new Button("Go Back");
        FlowPane root = new FlowPane(10, 10, error, goBack);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 150);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }}
        );

        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.show();
    }
}
