package com.company.views.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.company.views.menus.MainMenu.startMenu;
import static com.company.views.service.EmployeeService.showInfo;
import static com.company.views.service.EmployeeService.showReviews;

public class EmployeeMenu {

    public static void employeeMenu(String email,int id, Stage stage) throws SQLException{
        Button reviews = new Button("Show reviews");
        Button info = new Button("Show my info");
        Button back = new Button("Back");
        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, reviews, info, back);

        reviews.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showReviews(email,id, stage);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInfo(email,id, stage);
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    startMenu(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }



}
