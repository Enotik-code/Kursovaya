package com.company.views.menus;

import com.company.bean.Object;
import com.company.bean.User;
import com.company.enums.City;
import com.company.enums.Classifier;
import com.company.enums.Type;
import com.company.views.service.UserService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static com.company.connection.ConnectionMeth.*;
import static com.company.views.menus.MainMenu.startMenu;
import static com.company.views.service.ObjectService.returnObjects;

public class UserMenuView {

    public static UserService u = new UserService();

    public static void userMenu(String email, int id, Stage stage) {
        Button makeOrder = new Button("Make order");
        AnchorPane.setTopAnchor(makeOrder, 250.0);
        AnchorPane.setLeftAnchor(makeOrder, 60.0);
        AnchorPane.setRightAnchor(makeOrder, 60.0);

        Button giveFeedback = new Button("Give feedback");

        AnchorPane.setTopAnchor(giveFeedback, 300.0);
        AnchorPane.setLeftAnchor(giveFeedback, 60.0);
        AnchorPane.setRightAnchor(giveFeedback, 60.0);

        Button myInfo = new Button("My info");

        AnchorPane.setTopAnchor(myInfo, 350.0);
        AnchorPane.setLeftAnchor(myInfo, 60.0);
        AnchorPane.setRightAnchor(myInfo, 60.0);

        Button myOrders = new Button("My orders");

        AnchorPane.setTopAnchor(myOrders, 400.0);
        AnchorPane.setLeftAnchor(myOrders, 60.0);
        AnchorPane.setRightAnchor(myOrders, 60.0);

        Button back = new Button("Back");

        AnchorPane.setTopAnchor(back, 450.0);
        AnchorPane.setLeftAnchor(back, 60.0);
        AnchorPane.setRightAnchor(back, 60.0);


        makeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    makeOrderPart(email, id, stage);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        giveFeedback.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeFeedback(stage, email, id);
            }
        });
        myInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getInfoUser(u.getUser(email), stage, email, id);
            }
        });
        myOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //registration(stage);
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

        AnchorPane root = new AnchorPane(makeOrder, giveFeedback, myInfo, myOrders, back);
        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);

        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void getInfoUser(String info, Stage stage, String email, int id) {
        Label infoL = new Label(info);
        Button back = new Button("Back");
        FlowPane root = new FlowPane(Orientation.VERTICAL, 50, 50, infoL, back);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userMenu(email, id, stage);
            }
        });


        Scene scene = new Scene(root, 1366, 700);
        stage.setScene(scene);

        stage.setTitle("LogisticCo");

        stage.show();
    }

    public static void makeFeedback(Stage stage, String email, int id) {
        Label lbl = new Label("Ваш отзыв");
        TextArea textArea = new TextArea();
        textArea.setPrefColumnCount(15);
        textArea.setPrefRowCount(5);


        Label lbl2 = new Label("Укажите айди работника");
        TextArea textArea2 = new TextArea();
        textArea2.setPrefColumnCount(15);
        textArea2.setPrefRowCount(5);

        Button btn = new Button("Отправить!");
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 userMenu(email, id, stage);
                             }
                         }
        );

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    startConnection();
                    String query = " insert into reviews (id_review, id_employee, id_user, review)" +
                            " values (?, ?, ?, ?)";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(4, textArea.getText());
                    preparedStmt.setInt(2, Integer.parseInt(textArea2.getText()));
                    preparedStmt.setInt(3, id);
                    preparedStmt.setInt(1, (int) (Math.random() * 100));
                    preparedStmt.execute();
                    endConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                userMenu(email, id, stage);
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lbl, textArea, lbl2, textArea2, btn, back);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void makeOrderPart(String email, int id, Stage stage) throws SQLException {
        final char[] idType = new char[1];
        final char[] idClassifier = new char[1];
        boolean choose = false;
        final char[] idObject = new char[1];
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

        TableColumn<Object, Integer> idCity = new TableColumn<Object, Integer>("Id City");
        idCity.setCellValueFactory(new PropertyValueFactory<Object, Integer>("idCity"));
        table.getColumns().add(idCity);

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
                selectedLbl.setText("Выбранно: " + newValue);
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
                                 userMenu(email, id, stage);
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
                    preparedStmt.setInt(8, id);
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
                if (result.get() == buttonTypeOne) {
                    userMenu(email, id, stage);
                }
            }


    }

        );

    FlowPane root = new FlowPane(50, 50, lbl, langsListView, lbl2, langsListView2,
            selectedLbl, langsListViewTable, productId, textFieldProductId,
            city1, langsChoiceBox1City, city2, langsChoiceBox2City, perf, langsChoiceBox, enter);

    Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();


}
}




