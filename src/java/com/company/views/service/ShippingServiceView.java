package com.company.views.service;

import com.company.bean.Object;
import com.company.bean.Shipping;
import com.company.enums.City;
import com.company.enums.Classifier;
import com.company.enums.Type;
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
import static com.company.views.service.ObjectService.returnObjects;

public class ShippingServiceView {

    public static List<Shipping> shippingList = new ArrayList<>();

    static AdminMenuView adminMenuView= new AdminMenuView();

    public static List<Shipping> returnShipping() throws SQLException {
        startConnection();
        if (!shippingList.isEmpty()) shippingList.clear();
        ResultSet resultSet = statement.executeQuery("select * from shipping");
        while (resultSet.next()) {
            shippingList.add(new Shipping(
                    resultSet.getInt("id"),
                    resultSet.getInt("object_id"),
                     resultSet.getInt("user_id"),
                    resultSet.getInt("type_id"),
                    resultSet.getInt("classifier_id"),
                    resultSet.getString("destination"),
                    resultSet.getString("point_of_departure"),
                    resultSet.getBoolean("performed")));}
        resultSet.close();
        endConnection();
        return shippingList;
    }

    public static void addShipping(Stage stage) throws SQLException {
        final char[] idType = new char[1];
        final char[] idClassifier = new char[1];
        boolean choose = false;
        final char[] idObject = new char[1];
        Label userId = new Label("Введите id пользователя");
        TextField textFieldName = new TextField();

        Label lbl = new Label("Какой класс перевозки:");
        ObservableList<String> langs = FXCollections.observableArrayList(Classifier.ClassifierEnum.returnListClassifier());
        ListView<String> langsListView = new ListView<String>(langs);
        langsListView.setPrefSize(200, 200);
        MultipleSelectionModel<String> langsSelectionModel = langsListView.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {
                lbl.setText("Выбранно: \n" + newValue);
                idType[0] = newValue.charAt(0);
            }
        });


        Label lbl2 = new Label("Какой тип перевозки:");
        ObservableList<String> langs2 = FXCollections.observableArrayList(Type.TypeEnum.returnListType());
        ListView<String> langsListView2 = new ListView<String>(langs2);
        langsListView2.setPrefSize(200, 200);
        MultipleSelectionModel<String> langsSelectionModel2 = langsListView2.getSelectionModel();
        langsSelectionModel2.selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue2) {
                lbl2.setText("Выбранно: \n" + newValue2);
                idClassifier[0] = newValue2.charAt(0);
            }
        });


        ObservableList<Object> objects = FXCollections.observableArrayList(returnObjects());
        TableView<Object> table = new TableView<Object>(objects);
        table.setPrefWidth(400);
        table.setPrefHeight(400);

        TableColumn<Object, Integer> idColumn = new TableColumn<Object, Integer>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<Object, Integer>("idObject"));
        table.getColumns().add(idColumn);

        TableColumn<Object, String> descColumn = new TableColumn<Object, String>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<Object, String>("description"));
        table.getColumns().add(descColumn);

        TableColumn<Object, Integer> phoneNum = new TableColumn<Object, Integer>("Phone Number");
        phoneNum.setCellValueFactory(new PropertyValueFactory<Object, Integer>("phoneNumber"));
        table.getColumns().add(phoneNum);

        TableColumn<Object, String> siteManager = new TableColumn<Object, String>("Site Manager");
        siteManager.setCellValueFactory(new PropertyValueFactory<Object, String>("siteManager"));
        table.getColumns().add(siteManager);


        Label selectedLbl = new Label();
        // создаем список объектов
        ObservableList<TableView<Object>> langsTable = FXCollections.observableArrayList(table);
        ListView<TableView<Object>> langsListViewTable = new ListView<TableView<Object>>(langsTable);
        langsListViewTable.setPrefSize(300, 300);
        // получаем модель выбора элементов
        MultipleSelectionModel<TableView<Object>> langsSelectionModelTable = langsListViewTable.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModelTable.selectedItemProperty().addListener(new ChangeListener<TableView<Object>>() {

            @Override
            public void changed(ObservableValue<? extends TableView<Object>> observable, TableView<Object> oldValue, TableView<Object> newValue) {
                selectedLbl.setText("Выбранно: \n" + newValue);
            }
        });


        Label productId = new Label("Айди продукта");
        TextField textFieldProductId = new TextField();
        textFieldProductId.setPrefColumnCount(10);

        Label city1 = new Label("Город отправки");
        ObservableList<String> city1List = FXCollections.observableArrayList(City.CityEnum.getListCity());
        ChoiceBox<String> langsChoiceBox1City = new ChoiceBox<String>(city1List);

        Label lbl1City = new Label();
        langsChoiceBox1City.setOnAction(event -> lbl1City.setText(langsChoiceBox1City.getValue()));


        Label city2 = new Label("Город выгрузки");
        ObservableList<String> city2List = FXCollections.observableArrayList(City.CityEnum.getListCity());
        ChoiceBox<String> langsChoiceBox2City = new ChoiceBox<String>(city2List);

        Label lbl2City = new Label();
        langsChoiceBox2City.setOnAction(event -> lbl2City.setText(langsChoiceBox2City.getValue()));


        Label perf = new Label("Доставлен груз\n");
        ObservableList<String> langs3 = FXCollections.observableArrayList("Да", "Нет");
        ChoiceBox<String> langsChoiceBox = new ChoiceBox<String>(langs3);

        Label lbl3 = new Label();
        langsChoiceBox.setOnAction(event -> lbl3.setText(langsChoiceBox.getValue()));

        Button enter = new Button("Enter");
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 adminMenuView.manageEmployee(stage);
                             }
                         }
        );

        boolean finalChoose = choose;
        enter.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                  try {
                                      boolean choose;
                                      if (langsChoiceBox.getValue() == "да") choose = true;
                                      else choose = false;
                                      startConnection();
                                      String query = " insert into shipping (id, object_id,classifier_id, type_id, destination," +
                                              "performed, point_of_departure, user_id)" +
                                              " values (?, ?, ?,?,?, ?, ?,?)";
                                      PreparedStatement preparedStmt = connection.prepareStatement(query);
                                      preparedStmt.setInt(1, (int) (Math.random() * 100));
                                      preparedStmt.setInt(2, Integer.parseInt(textFieldProductId.getText()));
                                      preparedStmt.setInt(3, ((int) idClassifier[0] - 48));
                                      preparedStmt.setInt(4, ((int) idType[0] - 48));
                                      preparedStmt.setString(5, langsChoiceBox1City.getValue());
                                      preparedStmt.setString(7, langsChoiceBox2City.getValue());
                                      preparedStmt.setBoolean(6, choose);
                                      preparedStmt.setInt(8, Integer.parseInt(textFieldName.getText()));
                                      preparedStmt.execute();
                                      endConnection();
                                  } catch (SQLException e) {
                                      e.printStackTrace();
                                  }

                                  Alert goBack = new Alert(Alert.AlertType.CONFIRMATION);
                                  goBack.setHeaderText("Successfully\nGo back");
                                  ButtonType buttonTypeOne = new ButtonType("Back");

                                  goBack.getButtonTypes().setAll(buttonTypeOne);

                                  Optional<ButtonType> result = goBack.showAndWait();
                                  if (result.get() == buttonTypeOne) adminMenuView.manageShipping(stage);}
                          }

        );

        FlowPane root = new FlowPane(50, 50, userId, textFieldName, lbl, langsListView, lbl2, langsListView2,
                selectedLbl, langsListViewTable, productId, textFieldProductId,
                city1, langsChoiceBox1City, city2, langsChoiceBox2City, perf, langsChoiceBox, enter);

        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void showShipping(Stage stage) throws SQLException {
        ObservableList<Shipping> shippings = FXCollections.observableArrayList(returnShipping());
        TableView<Shipping> table = new TableView<Shipping>(shippings);
        table.setPrefWidth(600);
        table.setPrefHeight(600);

        TableColumn<Shipping, Integer> idShipping = new TableColumn<Shipping, Integer>("Id shipping");
        idShipping.setCellValueFactory(new PropertyValueFactory<Shipping, Integer>("id"));
        table.getColumns().add(idShipping);

        TableColumn<Shipping, Integer> idObject = new TableColumn<Shipping, Integer>("Id Object");
        idObject.setCellValueFactory(new PropertyValueFactory<Shipping, Integer>("id"));
        table.getColumns().add(idObject);

        TableColumn<Shipping, Integer> idClassifier = new TableColumn<Shipping, Integer>("Id Classifier");
        idClassifier.setCellValueFactory(new PropertyValueFactory<Shipping, Integer>("classifierId"));
        table.getColumns().add(idClassifier);

        TableColumn<Shipping, Integer> idType = new TableColumn<Shipping, Integer>("Id Type");
        idType.setCellValueFactory(new PropertyValueFactory<Shipping, Integer>("typeId"));
        table.getColumns().add(idType);

        TableColumn<Shipping, Integer> idUser = new TableColumn<Shipping, Integer>("Id User");
        idUser.setCellValueFactory(new PropertyValueFactory<Shipping, Integer>("UserId"));
        table.getColumns().add(idUser);


        TableColumn<Shipping, String> destination = new TableColumn<Shipping, String>("Destination");
        destination.setCellValueFactory(new PropertyValueFactory<Shipping, String>("destination"));
        table.getColumns().add(destination);

        TableColumn<Shipping, String> departure = new TableColumn<Shipping, String>("Point Of Departure");
        departure.setCellValueFactory(new PropertyValueFactory<Shipping, String>("pointOfDeparture"));
        table.getColumns().add(departure);

        TableColumn<Shipping,Boolean> performed = new TableColumn<Shipping, Boolean>("Performed");
        performed.setCellValueFactory(new PropertyValueFactory<Shipping, Boolean>("performed"));
        table.getColumns().add(performed);


        Button back = new Button("Back");

        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {

                                 adminMenuView.manageShipping(stage);
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

    public static void showObjects(Stage stage){
        ObservableList<Object> objects = null;
        try {
            objects = FXCollections.observableArrayList(returnObjects());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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


        Button back = new Button("Back");

        back.setOnAction(event -> adminMenuView.manageShipping(stage));
        FlowPane root = new FlowPane(Orientation.VERTICAL,50, 50, table, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();


    }
}


