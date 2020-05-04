package com.company.service;

import com.company.enums.Classifier;
import com.company.enums.Type;
import com.company.logger.Loggers;
import com.company.string.StringFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.connection.ConnectionMeth.*;
import static com.company.connection.ConnectionMeth.endConnection;

public class ShippingService {
    static Loggers log = new Loggers(com.company.service.ShippingService.class.getName());
    private int object_id;

    public void makeShipping(int idUser) {
        log.log.info(StringFile.ENTER_OBJECT);
        viewObjects("select * from object");
        object_id = EnterDataService.returnInt(99);
        if (object_id == 0) makeObject();
        try {
            startConnection();
            String query = " insert into shipping (id, object_id,classifier_id, type_id, destination," +
                    "performed, point_of_departure, user_id)" +
                    " values (?, ?, ?,?,?, ?, ?,?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, (int) (Math.random() * 100));
            preparedStmt.setInt(2, object_id);
            Classifier.ClassifierEnum.classifiersShow();
            log.log.info("Введите классификацию перевозки");
            preparedStmt.setInt(3, EnterDataService.returnInt(4));
            Type.TypeEnum.typesShow();
            log.log.info("Введите тип перевозки");
            preparedStmt.setInt(4, EnterDataService.returnInt(3));
            log.log.info(StringFile.ENTER_DESTINATION);
            preparedStmt.setString(5, EnterDataService.returnString());
            log.log.info(StringFile.ENTER_POINT_OF_DEPARTURE);
            preparedStmt.setString(7, EnterDataService.returnString());
            log.log.info(StringFile.ENTER_PERFOMED);
            preparedStmt.setBoolean(6, EnterDataService.returnBoolean());
            preparedStmt.setInt(8, idUser);
            log.log.info(StringFile.SUCCESSFULLY);
            preparedStmt.execute();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeObject() {
        try {
            startConnection();
            String query = " insert into object (idobject, description, phone_number, idcity, site_manager)" +
                    " values (?, ?, ?,?,?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            log.log.info(StringFile.ENTER_ID);
            preparedStmt.setInt(1, EnterDataService.returnInt(99));
            log.log.info(StringFile.ENTER_DESCRIPTION);
            preparedStmt.setString(2, EnterDataService.returnString());
            log.log.info(StringFile.ENTER_PHONE_NUMBER);
            preparedStmt.setString(3, EnterDataService.returnString());
            log.log.info(StringFile.ENTER_ID_CITY);
            preparedStmt.setInt(4, EnterDataService.returnInt(99));
            log.log.info("Введите менеджера");
            preparedStmt.setString(5, EnterDataService.returnString());
            preparedStmt.execute();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewObjects(String sql) {
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery(sql);
            log.log.info("+---+-----------------------+--------------------+--------------+--------------------+");
            log.log.info("| id|     description       |    phone number    |    id city   |     site manager   | ");
            log.log.info("+---+-----------------------+--------------------+--------------+--------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("idobject");
                String description = resultSet.getString("description");
                String phoneNumber = resultSet.getString("phone_number");
                int id_city = resultSet.getInt("idcity");
                String siteManager = resultSet.getString("site_manager");
                log.log.info("|" + id + "| " + description + " | " + phoneNumber + "    |" + id_city + "       |    " + siteManager + "   |");
            }
            log.log.info("+---+-----------------------+--------------------+--------------+--------------------+");
            resultSet.close();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewShipping(String sql) {
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery(sql);
            log.log.info("+---+-------------+--------------+----------+-------------+--------------------+-----------+---------+");
            log.log.info("| id| object id   | classifier id|  type id | destination | point of departure | performed | user id |");
            log.log.info("+---+-------------+--------------+----------+-------------+--------------------+-----------+---------+");
            while (resultSet.next()) {
                log.log.info("|" + resultSet.getInt("id") + "  |"
                        + resultSet.getInt("object_id") + "            |"
                        + resultSet.getInt("classifier_id") + "             |"
                        + resultSet.getInt("type_id") + "         |"
                        + resultSet.getString("destination") + "           |"
                        + resultSet.getString("point_of_departure") + "           |" +
                        resultSet.getBoolean("performed") + "          |"
                        + resultSet.getInt("user_id") + " |");
            }
            log.log.info("+---+-------------+--------------+----------+-------------+--------------------+-----------+---------+");
            resultSet.close();
            endConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void filtrationShipping() {
        while (true) {
            log.log.info(StringFile.FILTR_SHIP);
            log.log.info(StringFile.FILTR_OBJECT);
            log.log.info("3.Назад");
            switch (EnterDataService.returnInt(4)) {
                case 1:
                    while (true) {
                        log.log.info("1.По айди");
                        log.log.info("2.По айди объекта");
                        log.log.info("3.По айди класса перевозки");
                        log.log.info("4.По айди типа перевозки");
                        log.log.info("5.По отправной точке");
                        log.log.info("6.По конечной точке");
                        log.log.info("7.По состоянию");
                        log.log.info("8.По айди пользователя");
                        log.log.info("9.Назад");
                        switch (EnterDataService.returnInt(10)) {
                            case 1:
                                viewShipping("select * from shipping order by id");
                                break;
                            case 2:
                                viewShipping("select * from shipping order by object_id");
                                break;
                            case 3:
                                viewShipping("select * from shipping order by classifier_id");
                                break;
                            case 4:
                                viewShipping("select * from shipping order by type_id");
                                break;
                            case 5:
                                viewShipping("select * from shipping order by destination");
                                break;
                            case 6:
                                viewShipping("select * from shipping order by point_of_departure");
                                break;
                            case 7:
                                viewShipping("select * from shipping order by performed");
                                break;
                            case 8:
                                viewShipping("select * from shipping order by user_id");
                                break;
                            case 9:
                                return;
                            default:
                                log.log.error(StringFile.ERROR);
                                return;
                        }
                    }
                case 2:
                    while (true) {
                        log.log.info("1.По айди");
                        log.log.info("2.По описанию");
                        log.log.info("3.По номеру телефона");
                        log.log.info("4.По айди города");
                        log.log.info("5.По менеджеру");
                        log.log.info("6.Назад");
                        switch (EnterDataService.returnInt(7)) {
                            case 1:
                                viewObjects("select * from object order by idobject");
                                break;
                            case 2:
                                viewObjects("select * from object order by description");
                                break;
                            case 3:
                                viewObjects("select * from object order by phone_number");
                                break;
                            case 4:
                                viewObjects("select * from object order by id_city");
                                break;
                            case 5:
                                viewObjects("select * from object order by site_manager");
                                break;
                            case 6:
                                return;
                            default:
                                log.log.error(StringFile.ERROR);
                                return;
                        }
                    }
                case 3:
                    return;
                default:
                    log.log.error(StringFile.ERROR);
                    return;
            }
        }
    }
}
