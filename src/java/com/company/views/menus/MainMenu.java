package com.company.views.menus;

import com.company.service.RegistrationService;
import com.company.views.error.ErrorMessage;
import com.company.views.menus.AdminMenu;
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

import java.sql.SQLException;

import static com.company.views.menus.UserMenu.userMenu;

public class MainMenu {
    public static String loginConst = "login";
    public static String passConst = "pass";
    public static ErrorMessage errorMessage = new ErrorMessage();

    public static void startMenu(Stage stage) throws Exception{
        Button loginBtn = new Button("Login");
        loginBtn.setTooltip(new Tooltip("Click the button \nto login as admin, user \nor employee"));
        AnchorPane.setTopAnchor(loginBtn, 10.0);
        AnchorPane.setLeftAnchor(loginBtn, 60.0);
        AnchorPane.setRightAnchor(loginBtn, 60.0);

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setLoginAndPassword(stage);
            }
        });

        Button registrBtn = new Button("Registration");
        registrBtn.setTooltip(new Tooltip("Click the button \nto registration as \nnew user"));
        AnchorPane.setTopAnchor(registrBtn, 40.0);
        AnchorPane.setLeftAnchor(registrBtn, 60.0);
        AnchorPane.setRightAnchor(registrBtn, 60.0);

        registrBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            registration(stage);
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setTooltip(new Tooltip("Click the button \nto exit"));
        AnchorPane.setTopAnchor(exitBtn, 70.0);
        AnchorPane.setLeftAnchor(exitBtn, 60.0);
        AnchorPane.setRightAnchor(exitBtn, 60.0);

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Runtime.getRuntime().exit(0);
            }
        });

        FlowPane root = new FlowPane(10, 10, loginBtn,registrBtn, exitBtn);
        AnchorPane root2 = new AnchorPane(loginBtn,registrBtn,exitBtn);
        Scene scene = new Scene(root2, 300, 150);
        stage.setScene(scene);


        stage.setTitle("Start Menu");
        stage.show();
    }

    public static void setLoginAndPassword(Stage stage){
        String emailEn, passEn;
        Label login = new Label("Enter your email");
        Label password = new Label("Enter your password");
        TextField textFieldLogin = new TextField();
        TextField textFieldPassword = new TextField();
        textFieldLogin.setPrefColumnCount(11);
        textFieldPassword.setPrefColumnCount(15);
        Button enter = new Button("Enter");

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10,login,  textFieldLogin,password,  textFieldPassword , enter);
        Scene scene = new Scene(root, 250, 200);

        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.show();

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    RegistrationService.loginView(textFieldLogin.getText(),textFieldPassword.getText(),stage);
                } catch (SQLException throwables) {
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

        Button enter = new Button("Enter");

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10,login,  textFieldLogin,password,  textFieldPassword , enter);
        Scene scene = new Scene(root, 250, 200);

        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = (int) (Math.random()*50) + 50;
                RegistrationService.registration(passEn, loginEn, id);
                userMenu(id, stage);
            }}
        );

        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.show();
    }

}
