package com.company.views;
import com.company.views.menus.UserMenu;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static com.company.views.menus.MainMenu.startMenu;
import static com.company.views.service.EmployeeService.showReviews;


public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    int clicksLogin = 0, clicksExit = 0, clicksRegistration = 0;
    public String loginEq = "login", passwordEq = "password";
    UserMenu userMenu = new UserMenu();

        @Override
        public void start(Stage stage) throws Exception {
            //startMenu(stage);
            //userMenu.list(stage);
            showReviews(34, stage);
        }


        public void enterData(Stage stage, String text){
            Label lbl = new Label();
            TextArea textArea = new TextArea();
            textArea.setPrefColumnCount(15);
            textArea.setPrefRowCount(5);
            Button btn = new Button(text);
            btn.setOnAction(event -> lbl.setText("Input: " + textArea.getText()));
            FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, textArea, btn, lbl);
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 300, 250);

            stage.setScene(scene);
            stage.setTitle("TextArea in JavaFX");
            stage.show();
        }

        public void scrollPanel(Stage stage){
            Label lbl = new Label();
            TextArea textArea = new TextArea();
            textArea.setPrefColumnCount(15);
            textArea.setPrefRowCount(5);
            Button btn = new Button("Click");
            btn.setOnAction(event -> lbl.setText("Input: " + textArea.getText()));
            FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, textArea, btn, lbl);
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root, 300, 250);

            stage.setScene(scene);
            stage.setTitle("TextArea in JavaFX");
            stage.show();
        }

        public void slider(Stage stage){
            Label lbl = new Label("Value");

            Slider slider = new Slider(0.0, 20.0, 10.0);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setBlockIncrement(2.0);
            slider.setMajorTickUnit(5.0);
            slider.setMinorTickCount(4);
            slider.setSnapToTicks(true);

            slider.valueProperty().addListener(new ChangeListener<Number>(){

                public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                    lbl.setText("Slider Value: " + newValue);
                }
            });

            FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, slider, lbl);
            Scene scene = new Scene(root, 300, 150);

            stage.setScene(scene);
            stage.setTitle("Slider in JavaFX");
            stage.show();
        }



}