package com.company.views.menus;

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


public class AdminMenu {
    public static void adminMenu(Stage stage) {
        Button first = new Button("Manage employee");
        Button second = new Button("Manage users");
        Button third = new Button("Manage shipping");

        first.setTooltip(new Tooltip("1.Add employee\n2.Remove employee\n3.Show employees\n4.Show all position" +
                "\n5.Show reviews\n6.Filtration employee\n7.Exit"));
        second.setTooltip(new Tooltip("1.Add users\n2.Remove users\n3.Show employees\n4.Filtration users\n5.Exit"));
        third.setTooltip(new Tooltip("1.Add shipping\n2.Remove shipping\n3.Exit"));
        FlowPane root = new FlowPane(10, 10, first, second, third);

        first.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  adminMenu(stage);
                              }
                          }
        );

        second.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   adminMenu(stage);
                               }
                           }
        );

        third.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  adminMenu(stage);
                              }
                          }
        );

        Scene scene = new Scene(root, 600, 200);
        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.show();
    }

    public void manageEmployee(Stage stage) {
        Button first = new Button("Add employee");
        Button second = new Button("Remove employee");
        Button third = new Button("Show employees");
        Button four = new Button("Show position");
        Button five = new Button("Show reviews");
        Button six = new Button("Filtration employees");
        Button seven = new Button("Exit");

        first.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  adminMenu(stage);
                              }
                          }
        );
        second.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   adminMenu(stage);
                               }
                           }
        );
        third.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  adminMenu(stage);
                              }
                          }
        );
        four.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 //todo view
                             }
                         }
        );
        five.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 //todo
                             }
                         }
        );
        six.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // todo
                            }
                        }
        );
        seven.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  adminMenu(stage);
                              }
                          }
        );
    }

    public void manageUser(Stage stage){
        Button first = new Button("Add employee");
        Button second = new Button("Remove employee");
        Button third = new Button("Show employees");
    }
}