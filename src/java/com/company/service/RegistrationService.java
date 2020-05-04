package com.company.service;

import com.company.logger.Loggers;
import com.company.string.StringFile;
import com.company.views.menus.AdminMenu;
import com.company.views.menus.EmployeeMenu;
import com.company.views.menus.UserMenu;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.connection.ConnectionMeth.*;

public class RegistrationService {
    private String email, password, password2;
    static Loggers log = new Loggers(com.company.service.RegistrationService.class.getName());
    public static MenuService menuService = new MenuService();

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

    public static void loginView(String email, String password, Stage stage) throws SQLException {
        startConnection();
        String sql = "select * from user where email ='" + email + "'and password ='" + password + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getInt("id") > 1 && resultSet.getInt("id") < 50) {
                EmployeeMenu.employeeMenu(resultSet.getInt("id"), stage);
            }
            if (resultSet.getInt("id") > 50 && resultSet.getInt("id") < 99) {
                UserMenu.userMenu(resultSet.getInt("id"), stage);
            }
            if (resultSet.getInt("id") == 100) {
                AdminMenu.adminMenu(stage);
            }
        }
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
