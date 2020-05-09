package com.company.views.menus;

import com.company.views.service.ObjectService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.company.views.menus.MainMenu.startMenu;
import static com.company.views.service.EmployeeService.*;
import static com.company.views.service.ShippingServiceView.*;
import static com.company.views.service.UserService.*;


public class AdminMenuView {
    public ObjectService o = new ObjectService();

    public  void adminMenu(Stage stage) {
        Button first = new Button("Manage employee");
        Button second = new Button("Manage users");
        Button third = new Button("Manage shipping");
        Button back = new Button("Back");

        first.setTooltip(new Tooltip("1.Add employee\n2.Remove employee\n3.Show employees\n4.Show all position" +
                "\n5.Show reviews\n6.Filtration employee\n7.Exit"));
        second.setTooltip(new Tooltip("1.Add users\n2.Remove users\n3.Show employees\n4.Filtration users\n5.Exit"));
        third.setTooltip(new Tooltip("1.Add shipping\n2.Remove shipping\n3.Exit"));
        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 10, first, second, third, back);

        first.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  manageEmployee(stage);
                              }
                          }
        );

        second.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   manageUser(stage);
                               }
                           }
        );

        third.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  manageShipping(stage);
                              }
                          }
        );
        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 try {
                                     startMenu(stage);
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
        );
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public  void manageEmployee(Stage stage) {
        Button first = new Button("Add employee");
        Button second = new Button("Remove employee");
        Button third = new Button("Show employees");
        Button four = new Button("Show position");
        Button five = new Button("Show reviews");
        Button seven = new Button("Back");

        first.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      addEmployee(stage);
                                  } catch (SQLException throwables) {
                                      throwables.printStackTrace();
                                  }
                              }
                          }
        );
        second.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   try {
                                       deleteEmployee(stage);
                                   } catch (SQLException throwables) {
                                       throwables.printStackTrace();
                                   }
                               }
                           }
        );
        third.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      showEmployees(stage);
                                  } catch (SQLException throwables) {
                                      throwables.printStackTrace();
                                  }
                              }
                          }
        );
        four.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 try {
                                     showPosition(stage);
                                 } catch (SQLException throwables) {
                                     throwables.printStackTrace();
                                 }
                             }
                         }
        );
        five.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 try {
                                     showAllReviews(stage);
                                 } catch (SQLException throwables) {
                                     throwables.printStackTrace();
                                 }
                             }
                         }
        );
        seven.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      startMenu(stage);
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                              }
                          }
        );

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, first, second, third, four, five, seven);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();


    }

    public  void manageUser(Stage stage) {
        Button first = new Button("Add user");
        Button second = new Button("Remove user");
        Button third = new Button("Show users");

        first.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      addUser(stage);
                                  } catch (SQLException throwables) {
                                      throwables.printStackTrace();
                                  }
                              }
                          }
        );
        second.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   try {
                                       deleteUser(stage);
                                   } catch (SQLException throwables) {
                                       throwables.printStackTrace();
                                   }
                               }
                           }
        );
        third.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      showUsers(stage);
                                  } catch (SQLException throwables) {
                                      throwables.printStackTrace();
                                  }
                              }
                          }
        );
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 adminMenu(stage);
                             }
                         }
        );

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, first, second, third, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public void manageShipping(Stage stage) {
        Button addShipping = new Button("Add shipping");
        Button removeShipping = new Button("Delete shipping");
        Button showShipping = new Button("Show shipping");
        Button addObject = new Button("Add object");
        Button removeObject = new Button("Delete object");
        Button showObject = new Button("Show objects");
        Button back = new Button("Back");

        removeObject.setOnAction(event -> {
            try {
                o.removeObject(stage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        back.setOnAction(event -> adminMenu(stage));
        addShipping.setOnAction(event -> {
            try {
                addShipping(stage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        addObject.setOnAction(event -> {
            try {
                o.addObject(stage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        showShipping.setOnAction(event -> {
            try {
                showShipping(stage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        showObject.setOnAction(event -> showObjects(stage));
        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, addShipping, showShipping,addObject,  showObject, removeObject,  back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();

    }

}