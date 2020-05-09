package com.company.views.menus;

import com.company.service.RegistrationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.company.views.Main.getAlert;
import static com.company.views.service.UserService.addUser;


public class MainMenu {
    public static String loginConst = "login";
    public static String passConst = "pass";
    public static UserMenuView userMenuView = new UserMenuView();

    public static void startMenu(Stage stage) throws Exception{
        Button loginBtn = new Button("Login");
        loginBtn.setTooltip(new Tooltip("Click the button \nto login as admin, user \nor employee"));
        AnchorPane.setTopAnchor(loginBtn, 300.0);
        AnchorPane.setLeftAnchor(loginBtn, 500.0);
        AnchorPane.setRightAnchor(loginBtn, 500.0);

        loginBtn.setOnAction(event -> setLoginAndPassword(stage));

        Button registrBtn = new Button("Registration");
        registrBtn.setTooltip(new Tooltip("Click the button \nto registration as \nnew user"));
        AnchorPane.setTopAnchor(registrBtn, 350.0);
        AnchorPane.setLeftAnchor(registrBtn, 500.0);
        AnchorPane.setRightAnchor(registrBtn, 500.0);

        registrBtn.setOnAction(event -> {
            try {
                addUser(stage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setTooltip(new Tooltip("Click the button \nto exit"));
        AnchorPane.setTopAnchor(exitBtn, 400.0);
        AnchorPane.setLeftAnchor(exitBtn, 500.0);
        AnchorPane.setRightAnchor(exitBtn, 500.0);

        exitBtn.setOnAction(event -> Runtime.getRuntime().exit(0));

        AnchorPane root2 = new AnchorPane(loginBtn,registrBtn,exitBtn);
        Scene scene = new Scene(root2, 1366, 700);
        stage.setScene(scene);


        stage.setTitle("LogisticCo");
        stage.show();
    }

    public static void setLoginAndPassword(Stage stage){
        Label login = new Label("Email");
        Label password = new Label("Password");
        TextField textFieldLogin = new TextField();
        TextField textFieldPassword = new TextField();
        textFieldLogin.setPrefColumnCount(11);
        textFieldPassword.setPrefColumnCount(15);
        Button enter = new Button("Enter");

        FlowPane root = new FlowPane(Orientation.VERTICAL, 50, 10,login,  textFieldLogin,password,  textFieldPassword , enter);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 786);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    RegistrationService.loginView(textFieldLogin.getText(),textFieldPassword.getText(),stage);
                } catch (SQLException throwables) {
                    getAlert(stage, Alert.AlertType.ERROR,"Ошибка подключения к базе данных");
                    throwables.printStackTrace();
                }
            }}
        );

    }

    public static void registration(Stage stage){
        String loginEn, passEn;
        Label login = new Label("Enter your email");
        Label password = new Label("Enter your password");
        TextField textFieldLogin = new TextField();
        TextField textFieldPassword = new TextField();
        textFieldLogin.setPrefColumnCount(11);
        textFieldPassword.setPrefColumnCount(15);
        loginEn = textFieldLogin.getText();
        passEn = textFieldPassword.getText();
        Button back = new Button("Back");
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

        Button enter = new Button("Enter");

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10,login,  textFieldLogin,password,
                textFieldPassword , enter);
        Scene scene = new Scene(root, 250, 200);

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = (int) (Math.random()*50) + 50;
                RegistrationService.registration(passEn, loginEn, id);
                userMenuView.userMenu(loginEn,id, stage);
            }}
        );

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

}
