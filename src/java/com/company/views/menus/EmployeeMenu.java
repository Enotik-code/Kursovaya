package com.company.views.menus;

import com.company.bean.Review;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;


import static com.company.connection.ConnectionMeth.*;
import static com.company.views.service.EmployeeService.showInfo;
import static com.company.views.service.EmployeeService.showReviews;

public class EmployeeMenu {

    public static void employeeMenu(int id, Stage stage){
        Button reviews = new Button("Show reviews");
        Button info = new Button("Show my info");
        FlowPane root = new FlowPane(10, 10, reviews, info);

        reviews.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showReviews(id, stage);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInfo(stage);
            }
        });


        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Employee Menu");
        stage.show();
    }



}
