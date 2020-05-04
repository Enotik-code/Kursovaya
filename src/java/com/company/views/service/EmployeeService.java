package com.company.views.service;

import com.company.bean.Review;
import com.company.bean.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.company.connection.ConnectionMeth.*;

public class EmployeeService {
    static List<Review> reviewsList = new ArrayList<>();

    public static void showReviews(int id,Stage stage) throws SQLException{

        ObservableList<Review> reviews = FXCollections.observableArrayList(
                getReviews(id)
        );
        TableView<Review> table = new TableView<Review>(reviews);
        table.setPrefWidth(600);
        table.setPrefHeight(600);

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

        FlowPane root = new FlowPane(10, 10, table);
        Scene scene = new Scene(root, 600, 600);

        stage.setScene(scene);
        stage.setTitle("Table");
        stage.show();
    }


    public static void showInfo(Stage stage){

    }


    public static  List<Review> getReviews(int employeeId) throws SQLException {
        startConnection();
        ResultSet resultSet = null;
        resultSet = statement.executeQuery("select * from reviews where id_employee = '"+ employeeId + "'");
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
}
