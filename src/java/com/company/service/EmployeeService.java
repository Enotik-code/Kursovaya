package com.company.service;

import com.company.exceptions.SqlExceptionMessage;
import com.company.logger.Loggers;
import com.company.string.StringFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.connection.ConnectionMeth.*;
import static com.company.service.MenuService.userService;

public class EmployeeService {
    static Loggers log = new Loggers(AdminService.class.getName());

    int onHoliday, idPosition, userId, id;

    public void addEmployee() throws SQLException {
        showEmployee();
        userId = chooseEmployee();
        log.log.info(StringFile.ENTER_ON_HOLIDAY);
        log.log.info("1 - в отпуске. 0 - в работе");
        onHoliday = EnterDataService.returnInt(2);
        endConnection();
        getPosition("select * from position");
        log.log.info(StringFile.ENTER_POSITION);
        idPosition = EnterDataService.returnInt(10);
        startConnection();
        String query = "insert into employee (id, on_holiday, idPosition, user_id) values (?, ?, ?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1, (int) (Math.random() * 100));
        preparedStmt.setInt(2, onHoliday);
        preparedStmt.setInt(3, idPosition);
        preparedStmt.setInt(4, userId);
        log.log.info(StringFile.SUCCESSFULLY);
        preparedStmt.execute();
        endConnection();
    }

    public void deleteEmployee(){
        showEmployee();
        id = chooseEmployee();
        try {
            statement.executeUpdate("DELETE FROM employee WHERE id ='" + id + "'");
            log.log.info(StringFile.SUCCESSFULLY);
        } catch (SQLException ex) {
            System.out.println("Connection failed...");
        }
    }

    public void showEmployee() {
        userService.getAllUser("select * from user where id < 50 and id > 0");
    }

    public int chooseEmployee() {
        log.log.info(StringFile.ENTER_ID);
        return EnterDataService.returnInt(99);
    }

    public void getEmployee(String sql)throws SqlExceptionMessage {
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery(sql);
            log.log.info("+---+---------------+--------------+---------+");
            log.log.info("| id|  on_holiday   |  id_position | user_id |");
            log.log.info("+---+---------------+--------------+---------+");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                onHoliday = resultSet.getInt("on_holiday");
                idPosition = resultSet.getInt("idPosition");
                userId = resultSet.getInt("user_id");
                log.log.info("| " + id + " |      " + onHoliday + "        |        " + idPosition + "    |     " + userId + "    |");
            }
            log.log.info("+---+---------------+--------------+---------+");
            resultSet.close();
            endConnection();
        } catch (SQLException throwables) {
            throw  new SqlExceptionMessage(StringFile.SQL_MESSAGE);
        }
    }

    public void employeeFiltration() {
        while (true) {
            log.log.info("Выберете тип фильтрации:\n1.По параметрам работника\n2.По параметрам пользователя\n3.Выход");
            switch (EnterDataService.returnInt(4)) {
                case 1:
                    filtrationEmployee();
                    break;
                case 2:
                    filtrationEmployeeByUser();
                    break;
                case 3:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
            }
        }
    }

    public void filtrationEmployee() {
        while (true) {
            log.log.info("Выберете тип фильтрации:\n1.По айди\n2.По отпуску\n3.По айди позиции\n4.По айди пользователя\n5.Выход\n");
            switch (EnterDataService.returnInt(6)) {
                case 1:
                    try {
                        getEmployee("select * from employee order by id");
                    } catch (SqlExceptionMessage sqlExceptionMessage) {
                        sqlExceptionMessage.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        getEmployee("select * from employee order by on_holiday");
                    } catch (SqlExceptionMessage sqlExceptionMessage) {
                        sqlExceptionMessage.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        getEmployee("select * from employee order by idPosition");
                    } catch (SqlExceptionMessage sqlExceptionMessage) {
                        sqlExceptionMessage.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        getEmployee("select * from employee order by user_id");
                    } catch (SqlExceptionMessage sqlExceptionMessage) {
                        sqlExceptionMessage.printStackTrace();
                    }
                    break;
                case 5:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    break;
            }
        }
    }

    public void filtrationEmployeeByUser() {
        while (true) {
            log.log.info("Выберете тип фильтрации:1\n.По айди\n2.По имени\n3.По фамилии\n4.По почте\n5.Выход\n");
            switch (EnterDataService.returnInt(6)) {
                case 1:
                    userService.getAllUser("select * from user order by id");
                    break;
                case 2:
                    userService.getAllUser("select * from user order by name");
                    break;
                case 3:
                    userService.getAllUser("select * from user order by surname");
                    break;
                case 4:
                    userService.getAllUser("select * from user order by email");
                    break;
                case 5:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    break;
            }
        }
    }

    public void getPosition(String sql) {
        startConnection();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
            log.log.info("\nData:");
            log.log.info("+---+------------------+");
            log.log.info("| id|      position    |");
            log.log.info("+---+------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("idPosition");
                String position = resultSet.getString("position");
                log.log.info("|" + id + "| " + position + "    |");
            }
            log.log.info("+---+------------------+");
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getReviews(){
        startConnection();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select * from reviews");
            log.log.info("\nData:");
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            log.log.info("|id_review|id_employee|id_user|                     review                 |");
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            while (resultSet.next()) {
                int idReview = resultSet.getInt("id_review");
                int idEmployee = resultSet.getInt("id_employee");
                int idUser = resultSet.getInt("id_user");
                String review = resultSet.getString("review");
                log.log.info("|" + id + "        |    " + idEmployee + "      |  " + idUser + "   |" + review + "|");
            }
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getReviewsForOneEmployee(int employeeId) {
        startConnection();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select * from reviews where id_employee = '"+ employeeId + "'");
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            log.log.info("|id_review|id_employee|id_user|                     review                 |");
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            while (resultSet.next()) {
                int idReview = resultSet.getInt("id_review");
                int idEmployee = resultSet.getInt("id_employee");
                int idUser = resultSet.getInt("id_user");
                String review = resultSet.getString("review");
                log.log.info("|" + idReview + "        |    " + idEmployee + "      |  " + idUser + "   |" + review + "|");
            }
            log.log.info("+---------+-----------+-------+--------------------------------------------+");
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getPositionByEmployeeId(int id){
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery("select * from employee where user_id ='" + id + "'");
            log.log.info("+---+---------------+--------------+---------+");
            log.log.info("| id|  on_holiday   |  id_position | user_id |");
            log.log.info("+---+---------------+--------------+---------+");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                onHoliday = resultSet.getInt("on_holiday");
                idPosition = resultSet.getInt("idPosition");
                userId = resultSet.getInt("user_id");
                log.log.info("| " + id + " |      " + onHoliday + "        |        " + idPosition + "    |     " + userId + "    |");
            }
            log.log.info("+---+---------------+--------------+---------+");
            resultSet.close();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startConnection();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("select * from position where idPosition ='" +idPosition + "'");
            log.log.info("+---+------------------+");
            log.log.info("| id|      position    |");
            log.log.info("+---+------------------+");
            while (resultSet.next()) {
                int idPosition = resultSet.getInt("idPosition");
                String position = resultSet.getString("position");
                log.log.info("|" + idPosition + "| " + position + "    |");
            }
            log.log.info("+---+------------------+");
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getInfo(String email, int id){
    log.log.info("Мои отзывы:\n");
    getReviewsForOneEmployee(id);
    log.log.info("Моя информация:\n");
    userService.getUser(email);
    log.log.info("Моя позиция");
    getPositionByEmployeeId(id);
    }
}
