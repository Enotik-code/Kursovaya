package com.company.views.menus;

import com.company.bean.User;
import com.company.views.service.UserService;
import com.sun.org.apache.xerces.internal.xinclude.XPointerSchema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;
public class UserMenu {

    UserService u = new UserService();

    public static void userMenu(int id,Stage stage)
    {
        Button first = new Button("Make order");
        Button second = new Button("Show my orders");
        FlowPane root = new FlowPane(10, 10, first, second);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.show();
    }

    public void list(Stage stage) throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList(
              u.returnUsers()
        );
        TableView<User> table = new TableView<User>(users);
        table.setPrefWidth(600);
        table.setPrefHeight(600);

        TableColumn<User, Integer> idColumn = new TableColumn<User, Integer>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        table.getColumns().add(idColumn);

        TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        table.getColumns().add(emailColumn);

        TableColumn<User, String> nameColumn = new TableColumn<User, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        table.getColumns().add(nameColumn);

        TableColumn<User, String> surnameColumn = new TableColumn<User, String>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        table.getColumns().add(surnameColumn);

        TableColumn<User, String> patronymicColumn = new TableColumn<User, String>("Patronymic");
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<User, String>("patronymic"));
        table.getColumns().add(patronymicColumn);

        FlowPane root = new FlowPane(10, 10, table);
        Scene scene = new Scene(root, 600, 600);

        stage.setScene(scene);
        stage.setTitle("Table");
        stage.show();
    }
    }



