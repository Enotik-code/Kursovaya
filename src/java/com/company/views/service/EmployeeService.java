package com.company.views.service;

import com.company.bean.Position;
import com.company.bean.Review;
import com.company.bean.User;
import com.company.enums.PositionEnumClass;
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
import static com.company.views.menus.EmployeeMenu.employeeMenu;

public class EmployeeService {
    static List<Review> reviewsList = new ArrayList<>();
    static List<Position> positionList = new ArrayList<>();

    public static UserService u = new UserService();
    static AdminMenuView a = new AdminMenuView();

    public static List<Review> getReviews(int employeeId) throws SQLException {
        startConnection();
        ResultSet resultSet = null;
        reviewsList.clear();
        resultSet = statement.executeQuery("select * from reviews where id_employee = '" + employeeId + "'");
        while (resultSet.next()) {
            reviewsList.add(new Review(resultSet.getInt("id_review"),
                    resultSet.getInt("id_employee"),
                    resultSet.getInt("id_user"),
                    resultSet.getString("review")));
        }
        resultSet.close();
        endConnection();
        return reviewsList;
    }

    public static List<Review> getReviewsAll() throws SQLException {
        startConnection();
        ResultSet resultSet = null;
        reviewsList.clear();
        resultSet = statement.executeQuery("select * from reviews");
        while (resultSet.next()) {
            reviewsList.add(new Review(resultSet.getInt("id_review"),
                    resultSet.getInt("id_employee"),
                    resultSet.getInt("id_user"),
                    resultSet.getString("review")));
        }
        resultSet.close();
        endConnection();
        return reviewsList;
    }

    public static List<Position> getPosition() throws SQLException{
        startConnection();
        ResultSet resultSet = null;
        if(positionList.isEmpty()) positionList.clear();
        resultSet = statement.executeQuery("select * from position ");
        while (resultSet.next()) {
            positionList.add(new com.company.bean.Position(resultSet.getInt("idPosition"),
                    resultSet.getString("position")));
        }
        resultSet.close();
        endConnection();
        return positionList;
    }

    public static String getPositionByEmployeeId(int id) {
        int onHoliday = 0, idPosition = 0, userId;
        String position = null, vOtnuske = null;
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery("select * from employee where user_id ='" + id + "'");

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                onHoliday = resultSet.getInt("on_holiday");
                idPosition = resultSet.getInt("idPosition");
                userId = resultSet.getInt("user_id");
            }
            resultSet.close();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startConnection();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select * from position where idPosition ='" + idPosition + "'");
            while (resultSet.next()) {
                position = resultSet.getString("position");
            }
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (onHoliday == 1) vOtnuske = "да";
        else vOtnuske = "нет";

        String result = "Моя позиция: " + position + "\nВ отпуске: " + vOtnuske;
        return result;
    }

    public static void showReviews(String email, int id, Stage stage) throws SQLException {
        ObservableList<Review> reviews = FXCollections.observableArrayList(
                getReviews(id)
        );
        TableView<Review> table = new TableView<Review>(reviews);
        table.setPrefWidth(300);
        table.setPrefHeight(300);

        TableColumn<Review, Integer> idRColumn = new TableColumn<Review, Integer>("Id Review");
        idRColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("id"));
        table.getColumns().add(idRColumn);

        TableColumn<Review, Integer> idEColumn = new TableColumn<Review, Integer>("Id Employee");
        idEColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("idEmployee"));
        table.getColumns().add(idEColumn);

        TableColumn<Review, Integer> idUColumn = new TableColumn<Review, Integer>("Id User");
        idUColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("IdUser"));
        table.getColumns().add(idUColumn);

        TableColumn<Review, String> reviewColumn = new TableColumn<Review, String>("Review");
        reviewColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("review"));
        table.getColumns().add(reviewColumn);
        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    employeeMenu(email, id, stage);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, table, back);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void showInfo(String email, int id, Stage stage) {
        Button back = new Button("Back");
        String infoS = u.getUser(email) + getPositionByEmployeeId(id);
        Label info = new Label(infoS);
        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, info, back);
        root.setAlignment(Pos.CENTER);

        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    employeeMenu(email, id, stage);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);

        stage.setTitle("LogisticCo");

        stage.show();
    }

    public static void addEmployee(Stage stage) throws SQLException {
        final long[] userId = new long[1];
        Label lbl = new Label("Выберите пользователя");

        ObservableList<User> users = FXCollections.observableArrayList(
                u.returnUsers()
        );
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

        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue != null) {
                    lbl.setText("Selected: " + newValue.getSurname());
                    userId[0] = newValue.getId();
                }
            }
        });

        Label perf = new Label("В отпуске\n");
        ObservableList<String> langs1 = FXCollections.observableArrayList("Да", "Нет");
        ChoiceBox<String> langsChoiceBox = new ChoiceBox<String>(langs1);

        Label lbl1 = new Label();
        langsChoiceBox.setOnAction(event -> lbl1.setText(langsChoiceBox.getValue()));


        Label position = new Label("Позиция\n");
        ObservableList<String> langs2 = FXCollections.observableArrayList(PositionEnumClass.PositionEnum.PositionShow());
        ChoiceBox<String> langsChoiceBox2 = new ChoiceBox<String>(langs2);

        Label lbl2 = new Label();
        langsChoiceBox.setOnAction(event -> lbl2.setText(langsChoiceBox2.getValue()));

        Button enter = new Button("Enter");
        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                a.manageEmployee(stage);
            }
        });

        enter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    int otpusk;
                    if (langsChoiceBox.getValue() == "Нет") otpusk = 0;
                    else otpusk = 1;

                    startConnection();
                    String query = "insert into employee (id, on_holiday, idPosition, user_id) values (?, ?, ?, ?)";
                    PreparedStatement preparedStmt = null;
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, (int) (Math.random() * 100));
                    preparedStmt.setInt(2, otpusk);
                    preparedStmt.setInt(3, ((int) langsChoiceBox2.getValue().charAt(0) - 48));
                    preparedStmt.setInt(4, (int) userId[0]);
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
                    a.adminMenu(stage);}


        }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, lbl, table, perf, langsChoiceBox, position, langsChoiceBox2, enter);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void deleteEmployee(Stage stage) throws SQLException {
        Label lbl = new Label("Выберите пользователя");

        ObservableList<User> users = FXCollections.observableArrayList(u.returnEmployee());
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

        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue != null) {
                    lbl.setText(String.valueOf(newValue.getId()));
                }
            }
        });
        Button delete = new Button("Delete");

        delete.setOnAction(event -> {
            try {
                startConnection();
                statement.executeUpdate("DELETE FROM user WHERE id ='" + lbl.getText() + "'");
                endConnection();
            } catch (SQLException ex) {
                System.out.println("Connection failed...");
            }
            Alert goBack = new Alert(Alert.AlertType.CONFIRMATION);
            goBack.setHeaderText("Successfully\nGo back");
            ButtonType buttonTypeOne = new ButtonType("Back");

            goBack.getButtonTypes().setAll(buttonTypeOne);

            Optional<ButtonType> result = goBack.showAndWait();
            if (result.get() == buttonTypeOne){
                a.manageEmployee(stage);}

    }

        );

        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, lbl, table, delete);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void showEmployees(Stage stage) throws  SQLException{
        ObservableList<User> users = FXCollections.observableArrayList(u.returnEmployee());
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
                               a.manageEmployee(stage);
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

    public static void showAllReviews(Stage stage) throws SQLException{
        ObservableList<Review> reviews = FXCollections.observableArrayList(getReviewsAll());
        TableView<Review> table = new TableView<Review>(reviews);
        table.setPrefWidth(300);
        table.setPrefHeight(300);

        TableColumn<Review, Integer> idRColumn = new TableColumn<Review, Integer>("Id Review");
        idRColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("id"));
        table.getColumns().add(idRColumn);

        TableColumn<Review, Integer> idEColumn = new TableColumn<Review, Integer>("Id Employee");
        idEColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("idEmployee"));
        table.getColumns().add(idEColumn);

        TableColumn<Review, Integer> idUColumn = new TableColumn<Review, Integer>("Id User");
        idUColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("IdUser"));
        table.getColumns().add(idUColumn);

        TableColumn<Review, String> reviewColumn = new TableColumn<Review, String>("Review");
        reviewColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("review"));
        table.getColumns().add(reviewColumn);
        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                a.manageEmployee(stage);
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, table, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void showPosition(Stage stage) throws SQLException{
        ObservableList<Position> position = FXCollections.observableArrayList(getPosition());
        TableView<Position> table = new TableView<Position>(position);
        table.setPrefWidth(300);
        table.setPrefHeight(300);

        TableColumn<Position, Integer> idUColumn = new TableColumn<Position, Integer>("Id Position");
        idUColumn.setCellValueFactory(new PropertyValueFactory<Position, Integer>("id"));
        table.getColumns().add(idUColumn);

        TableColumn<Position, String> reviewColumn = new TableColumn<Position, String>("Position");
        reviewColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("position"));
        table.getColumns().add(reviewColumn);

        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                a.manageEmployee(stage);
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, table, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();




    }
}
