package com.company.service;

import com.company.enums.Classifier;
import com.company.enums.Type;
import com.company.string.StringFile;
import com.company.logger.Loggers;

import java.sql.*;

import static com.company.connection.ConnectionMeth.*;
import static com.company.service.MenuService.employeeService;

public class UserService {
    static Loggers log = new Loggers(com.company.service.UserService.class.getName());
    private String emailForSQL, nameForSQL;
    private int choise = 0;

    public void setUserData(String email) {
        try {
            startConnection();
            getUser(email);
            while (choise != 7) {
                log.log.info(StringFile.WHAT_TO_CHANGE + "7.Назад\n");
                log.log.info(StringFile.UR_CHOISE);
                choise = EnterDataService.returnInt(8);
                switch (choise) {
                    case 1:
                        log.log.info(StringFile.ENTER_NAME);
                        nameForSQL = EnterDataService.returnString();
                        String sql = "update user set name = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        PreparedStatement preparedStmt = connection.prepareStatement(sql);
                        preparedStmt.execute();
                        break;
                    case 2:
                        log.log.info(StringFile.ENTER_SURNAME);
                        nameForSQL = EnterDataService.returnString();
                        sql = "update user set surname = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        preparedStmt = connection.prepareStatement(sql);
                        break;
                    case 3:
                        log.log.info(StringFile.ENTER_PATRONYMIC);
                        nameForSQL = EnterDataService.returnString();
                        sql = "update user set patronymic = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        preparedStmt = connection.prepareStatement(sql);
                        break;
                    case 4:
                        log.log.info(StringFile.ENTER_PHONE_NUMBER);
                        nameForSQL = EnterDataService.returnString();
                        sql = "update user set number = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        preparedStmt = connection.prepareStatement(sql);
                        preparedStmt.execute();
                        break;
                    case 5:
                        log.log.info(StringFile.ENTER_EMAIL);
                        nameForSQL = EnterDataService.returnString();
                        sql = "update user set email = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        preparedStmt = connection.prepareStatement(sql);
                        preparedStmt.execute();
                        break;
                    case 6:
                        log.log.info(StringFile.ENTER_PASSWORD);
                        nameForSQL = EnterDataService.returnString();
                        sql = "update user set password = '" + nameForSQL + "' where email ='" + emailForSQL + "'";
                        preparedStmt = connection.prepareStatement(sql);
                        preparedStmt.execute();
                        break;
                    case 7:
                        choise = 7;
                        break;
                    default:
                        log.log.error(StringFile.THIS_NOT_A_NUMBER);
                        break;
                }
            }
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllUser(String sql) {
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery(sql);
            log.log.info("Retrieving data from database");
            log.log.info("\nData:");
            log.log.info("+---+------------------+--------------+-------------+--------------------+");
            log.log.info("| id|      surname     |    name      |     email   |   date of birthday | ");
            log.log.info("+---+------------------+--------------+-------------+--------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String dateOfBirthaday = resultSet.getString("date");
                log.log.info("|" + id + "| " + surname + "          | " + name + "    |" + email + "      |" + "  " + dateOfBirthaday + "   |");
            }
            log.log.info("+---+------------------+--------------+-------------+--------------------+");
            resultSet.close();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser() {
    }

    public void deleteUser() {
        log.log.info(StringFile.ENTER_EMAIL);
        emailForSQL = EnterDataService.returnString();
        try {
            statement.executeUpdate("DELETE FROM user WHERE email ='" + emailForSQL + "'");
            log.log.info(StringFile.SUCCESSFULLY);
        } catch (Exception ex) {
            System.out.println("Connection failed...");
        }
    }

    public void getUser(String email) {
        startConnection();
        String query = "select * from user where email ='" + email + "'";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String patronymic = resultSet.getString("patronymic");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String number = resultSet.getString("number");
                String date = resultSet.getString("date");
                log.log.info(StringFile.ID + ": " + id);
                log.log.info(StringFile.EMAIL + ": " + email);
                log.log.info(StringFile.SURNAME + ": " + surname);
                log.log.info(StringFile.NAME + ": " + name);
                log.log.info(StringFile.PATRONYMIC + ": " + patronymic);
                log.log.info(StringFile.PHONE_NUMBER + ": " + number);
                log.log.info(StringFile.DAY_OF_BIRTHDAY + ": " + date);
                log.log.info("\n");
            }
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void makeReview(int idUser) {
        log.log.info("Укажите айди работника которому вы хотите оставить отзыв\n");
        employeeService.showEmployee();
        int id = EnterDataService.returnInt(50);
        log.log.info("Ваш отзыв");
        String review = EnterDataService.returnString();
        try {
            startConnection();
            String query = " insert into reviews (id_review, id_employee, id_user, review)" +
                    " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(4, review);
            preparedStmt.setInt(2, id);
            preparedStmt.setInt(3, idUser);
            preparedStmt.setInt(1, (int) (Math.random() * 100));
            preparedStmt.execute();
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
