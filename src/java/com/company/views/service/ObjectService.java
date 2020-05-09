
package com.company.views.service;

import com.company.bean.Object;
import com.company.views.menus.AdminMenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.company.connection.ConnectionMeth.*;

public class ObjectService {
    static List<Object> objects = new ArrayList<>();
    static AdminMenuView adminMenuView = new AdminMenuView();

    public static List<Object> returnObjects() throws SQLException {
        startConnection();
        if (!objects.isEmpty()) objects.clear();
        ResultSet resultSet = statement.executeQuery("select * from object");
        while (resultSet.next()) {
            objects.add(new Object(resultSet.getInt("idobject"),
                    resultSet.getString("description"),
                    resultSet.getInt("phone_number"),
                    resultSet.getString("site_manager")));
        }
        resultSet.close();
        endConnection();
        return objects;
    }

    public void addObject(Stage stage) throws SQLException {
        Label description = new Label("Description");
        Label phoneNumber = new Label("Phone number");
        Label siteManager = new Label("Site manager");


        TextField textFieldDescription = new TextField();
        TextField textFieldNumber = new TextField();
        TextField textFieldSiteManager = new TextField();


        Button enter = new Button("Enter");
        Button back = new Button("Back");

        enter.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      startConnection();
                                      String query = " insert into object (idobject, description , phone_number, site_manager)" +
                                              " values (?, ?, ?, ?)";
                                      PreparedStatement preparedStmt = connection.prepareStatement(query);
                                      preparedStmt.setString(2, textFieldDescription.getText());
                                      preparedStmt.setString(3, textFieldNumber.getText());
                                      preparedStmt.setString(4, textFieldSiteManager.getText());
                                      preparedStmt.setInt(1, (int) (Math.random() * 50));
                                      preparedStmt.execute();
                                      endConnection();
                                  } catch (SQLException throwables) {
                                      throwables.printStackTrace();
                                  }

                                  Alert goBack = new Alert(Alert.AlertType.CONFIRMATION);
                                  goBack.setHeaderText("Successfully\nGo back");
                                  ButtonType buttonTypeOne = new ButtonType("Back");

                                  goBack.getButtonTypes().setAll(buttonTypeOne);

                                  Optional<ButtonType> result = goBack.showAndWait();
                                  if (result.get() == buttonTypeOne) adminMenuView.manageShipping(stage);
                              }
                          }
        );

        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, description, textFieldDescription, phoneNumber, textFieldNumber, siteManager, textFieldSiteManager, enter, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();

    }

    public void removeObject(Stage stage) throws SQLException {
        final int[] description = new int[2];
        ObservableList<Object> objects = FXCollections.observableArrayList(returnObjects());
        TableView<Object> table = new TableView<Object>(objects);
        table.setPrefWidth(400);
        table.setPrefHeight(400);

        TableColumn<Object, String> descColumn = new TableColumn<Object, String>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<Object, String>("description"));
        table.getColumns().add(descColumn);

        TableColumn<Object, Integer> phoneNum = new TableColumn<Object, Integer>("Phone Number");
        phoneNum.setCellValueFactory(new PropertyValueFactory<Object, Integer>("phoneNumber"));
        table.getColumns().add(phoneNum);

        TableColumn<Object, String> siteManager = new TableColumn<Object, String>("Site Manager");
        siteManager.setCellValueFactory(new PropertyValueFactory<Object, String>("siteManager"));
        table.getColumns().add(siteManager);
        Button delete = new Button("Delete");
        Button back = new Button("Back");
        back.setOnAction(event -> adminMenuView.manageShipping(stage));
        Label lbl = new Label();
        TableView.TableViewSelectionModel<Object> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    lbl.setText(String.valueOf(newValue.getIdObject()));
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    startConnection();
                    statement.executeUpdate("DELETE FROM object WHERE idobject ='" + lbl.getText() + "'");
                    endConnection();
                } catch (SQLException throwables) {
                    System.out.println("траблы");
                    throwables.printStackTrace();
                }

                Alert goBack = new Alert(Alert.AlertType.CONFIRMATION);
                goBack.setHeaderText("Successfully\nGo back");
                ButtonType buttonTypeOne = new ButtonType("Back");

                goBack.getButtonTypes().setAll(buttonTypeOne);

                Optional<ButtonType> result = goBack.showAndWait();
                if (result.get() == buttonTypeOne) adminMenuView.manageShipping(stage);
        }});

        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, table,back, delete);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();


    }
}