package com.company.views.service;

import com.company.bean.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.connection.ConnectionMeth.*;

public class UserService {
    static List<User> users = new ArrayList<>();

    static AdminMenuView adminMenuView = new com.company.views.menus.AdminMenuView();

    public List<User> returnEmployee() throws SQLException {
        startConnection();
        if (!users.isEmpty()) users.clear();
        ResultSet resultSet = statement.executeQuery("select * from user where id < 50");
        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("surname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("number"),
                    resultSet.getString("date")));
        }
        resultSet.close();
        endConnection();
        return users;
    }
    public static List<User> returnUsers() throws SQLException {
        startConnection();
        if (!users.isEmpty()) users.clear();
        ResultSet resultSet = statement.executeQuery("select * from user where id > 50 and id < 99");
        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("surname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("number"),
                    resultSet.getString("date")));
        }
        resultSet.close();
        endConnection();
        return users;
    }

    public String getUser(String email) {
        int id = 0;
        String name = null, date = null, surname = null, patronymic = null, number = null;
        startConnection();
        String query = "select * from user where email ='" + email + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                surname = resultSet.getString("surname");
                patronymic = resultSet.getString("patronymic");
                number = resultSet.getString("number");
                date = resultSet.getString("date");
            }
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String result = "Мой айди: " + id + "\nИмя :" + name + "\nФамилия :" + surname + "\nОтчество :" + patronymic
                + "\nНомер :" + number + "\nДата рождения :" + date + "\n";
        return result;
    }

    public static void showUsers(Stage stage) throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList(returnUsers());
        TableView<User> table = new TableView<User>(users);
        table.setPrefWidth(600);
        table.setPrefHeight(600);

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

        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 int id = (int) (Math.random() * 50) + 50;
                                 adminMenuView.manageUser(stage);
                             }
                         }
        );

        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, table, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void addUser(Stage stage) throws SQLException {
        Label name = new Label("Name");
        Label surname = new Label("Surname");
        Label patronymic = new Label("Patronymic");
        Label number = new Label("Phone Number");
        Label email = new Label("Email");
        Label password = new Label("Password");
        Label date = new Label("Date of birthday");

        TextField textFieldName = new TextField();
        TextField textFieldSurname = new TextField();
        TextField textFieldPatronymic = new TextField();
        TextField textFieldEmail = new TextField();
        TextField textFieldNumber = new TextField();
        TextField textFieldPassword = new TextField();
        TextField textFieldDate = new TextField();

        textFieldName.setPrefColumnCount(11);
        textFieldSurname.setPrefColumnCount(15);
        textFieldPatronymic.setPrefColumnCount(19);
        textFieldEmail.setPrefColumnCount(23);
        textFieldPassword.setPrefColumnCount(27);
        textFieldDate.setPrefColumnCount(31);

        Button enter = new Button("Enter");
        Button back = new Button("Back");

        enter.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      startConnection();
                                      String query = " insert into user (id, date, name, number, password, email, patronymic, surname)" +
                                              " values (?, ?, ?, ?, ?, ?, ?, ?)";
                                      PreparedStatement preparedStmt = connection.prepareStatement(query);
                                      preparedStmt.setString(2, textFieldDate.getText());
                                      preparedStmt.setString(5, textFieldPassword.getText());
                                      preparedStmt.setString(6, textFieldEmail.getText());
                                      preparedStmt.setString(7, textFieldPatronymic.getText());
                                      preparedStmt.setString(8, textFieldSurname.getText());
                                      preparedStmt.setString(3, textFieldName.getText());
                                      preparedStmt.setString(4, textFieldNumber.getText());
                                      preparedStmt.setInt(1, ((int) (Math.random() * 50) + 50));
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
                                  if (result.get() == buttonTypeOne){
                                      adminMenuView.manageUser(stage);}
                              }
                          });
        back.setOnAction(event -> adminMenuView.manageUser(stage));

        FlowPane root = new FlowPane(Orientation.VERTICAL, 50, 10, name, textFieldName, surname, textFieldSurname,
                patronymic, textFieldPatronymic, email, textFieldEmail,
                password, textFieldPassword, number, textFieldNumber,
                date, textFieldDate, enter, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 786);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();

    }

    public static void deleteUser(Stage stage) throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList(returnUsers());

        Label lbl = new Label();

        TableView<User> table = new TableView<User>(users);
        table.setPrefWidth(600);
        table.setPrefHeight(600);

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

        TableColumn<User, String> numberColumn = new TableColumn<User, String>("Phone Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("number"));
        table.getColumns().add(numberColumn);


        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue != null) lbl.setText(newValue.getName());
            }
        });
        Button delete = new Button("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    startConnection();
                    statement.executeUpdate("DELETE FROM user WHERE name ='" + lbl.getText() + "'");
                    endConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                Alert goBack = new Alert(Alert.AlertType.CONFIRMATION);
                goBack.setHeaderText("Successfully\nGo back");
                ButtonType buttonTypeOne = new ButtonType("Back");

                goBack.getButtonTypes().setAll(buttonTypeOne);

                Optional<ButtonType> result = goBack.showAndWait();
                if (result.get() == buttonTypeOne) { adminMenuView.manageUser(stage);
                }
            }});

        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, table, delete);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }
}
