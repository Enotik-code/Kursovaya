package com.company.service;

import com.company.logger.Loggers;
import com.company.string.StringFile;
import com.company.views.menus.AdminMenuView;
import com.company.views.menus.EmployeeMenu;
import com.company.views.service.UserService;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.connection.ConnectionMeth.*;
import static com.company.views.Main.getAlert;
import static com.company.views.menus.UserMenuView.userMenu;

public class RegistrationService {
    private String email, password, password2;
    static Loggers log = new Loggers(com.company.service.RegistrationService.class.getName());
    public static MenuService menuService = new MenuService();
    public static com.company.views.menus.UserMenuView userMenu = new com.company.views.menus.UserMenuView();

    public void login() {
        log.log.info(StringFile.ENTER_EMAIL);
        email = EnterDataService.returnString();
        log.log.info(StringFile.ENTER_PASSWORD);
        password = EnterDataService.returnString();
        startConnection();
        String sql = "select * from user where email ='" + email +"'and password ='" + password+"'";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if(resultSet.getInt("id") > 1 & resultSet.getInt("id") < 50) {
                        log.log.info(StringFile.SUCCESSFULLY, StringFile.LOG_AS_EMPLOYEE);
                        menuService.employeeMenu(email, resultSet.getInt("id"));
                } //employee
                if(resultSet.getInt("id") > 50 & resultSet.getInt("id") < 99) {
                            log.log.info(StringFile.SUCCESSFULLY, StringFile.LOG_AS_USER);
                            menuService.userMenu(email, resultSet.getInt("id")); }
                if(resultSet.getInt("id") ==  100){
                        log.log.info(StringFile.SUCCESSFULLY, StringFile.LOG_AS_ADMIN);
                        menuService.adminMenu(email);
                    }
                }
        resultSet.close();
            endConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        log.log.error(StringFile.ERROR);
    }

    static AdminMenuView a = new AdminMenuView();
    public static void loginView(String email, String password, Stage stage) throws SQLException {
        startConnection();
        String sql = "select * from user where email ='" + email + "'and password ='" + password + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getInt("id") > 1 && resultSet.getInt("id") < 50) {
                getAlert(stage, Alert.AlertType.INFORMATION, "Login as Employee");
                EmployeeMenu.employeeMenu(email,resultSet.getInt("id"), stage);
                return;
            }
            if (resultSet.getInt("id") > 50 && resultSet.getInt("id") < 99) {
                getAlert(stage, Alert.AlertType.INFORMATION, "Login as User    ");
                userMenu(email,resultSet.getInt("id"), stage);
                return;
            }
            if (resultSet.getInt("id") == 100) {
                getAlert(stage, Alert.AlertType.INFORMATION, "Login as Admin");
                a.adminMenu(stage);
                return;
            }
        }
            getAlert(stage, Alert.AlertType.ERROR, "Wrong login or password");
            resultSet.close();
            endConnection();
    }

    public static void registration(String passwordStr, String emailStr, int id) {
        try {
            startConnection();
            String query = " insert into user (id, email, password)" +
                    " values (?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(3, emailStr);
            preparedStmt.setString(2, passwordStr);
            preparedStmt.setInt(1,id );
            preparedStmt.execute();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void registrationConsole() {
        String log, pass;
        try {
            startConnection();
            String query = " insert into user (id, email, password)" +
                    " values (?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            System.out.println("login");
            log = EnterDataService.returnString();
            System.out.println("pass");
            pass = EnterDataService.returnString();
            preparedStmt.setString(3, log);
            preparedStmt.setString(2, pass);
            preparedStmt.setInt(1, (int) (Math.random()*100));
            preparedStmt.execute();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
