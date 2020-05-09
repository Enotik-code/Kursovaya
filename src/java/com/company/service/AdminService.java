package com.company.service;

import com.company.enums.Classifier;
import com.company.enums.Type;
import com.company.exceptions.SqlExceptionMessage;
import com.company.logger.Loggers;
import com.company.string.StringFile;
import jdk.nashorn.internal.ir.EmptyNode;

import java.sql.SQLException;

import static com.company.service.MenuService.*;

public class AdminService {
    static Loggers log = new Loggers(com.company.service.AdminService.class.getName());
    static int idShip  =0 ;

    public void manageUser() {
        while (true) {
            log.log.info(StringFile.ADD_USER);
            log.log.info(StringFile.DELETE_USER);
            log.log.info(StringFile.SHOW_USER);
            log.log.info(StringFile.FILTRATION_USER);
            log.log.info("5.Выход");
            switch (EnterDataService.returnInt(6)) {
                case 1:
                    userService.addUser();
                    break;
                case 2:
                    userService.deleteUser();
                    break;
                case 3:
                    userService.getAllUser("select * from user where id > 50 and id < 100");
                    break;
                case 4:
                    employeeService.filtrationEmployeeByUser();
                    break;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    return;
            }
        }
    }

    //выполнено!
    public void manageEmployee() throws SQLException {
        while (true) {
            log.log.info(StringFile.ADD_EMPLOYEE);
            log.log.info(StringFile.DELETE_EMPLOYEE);
            log.log.info(StringFile.SHOW_EMPLOYEE);
            log.log.info(StringFile.SHOW_POSITION);
            log.log.info(StringFile.SHOW_REVIEW);
            log.log.info(StringFile.FILTRATION_EMPLOYEE);
            log.log.info("7.Выход");
            switch (EnterDataService.returnInt(8)) {
                case 1:
                        employeeService.addEmployee();
                    break;
                case 2:
                        employeeService.deleteEmployee();
                    break;
                case 3:
                    try {
                        employeeService.getEmployee("select * from employee");
                    } catch (SqlExceptionMessage sqlExceptionMessage) {
                        sqlExceptionMessage.printStackTrace();
                    }
                    userService.getAllUser("select * from user where id > 0 and id < 50");
                    break;
                case 4:
                    employeeService.getPosition("select * from position");
                    break;
                case 5:
                    employeeService.getReviews();
                    break;
                case 6:
                    employeeService.employeeFiltration();
                    break;
                case 7:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    return;
            }
        }
    }

    //выполнено!
    public void manageShipping() {
        while (true) {
            log.log.info(StringFile.SHOW_SHIP);
            log.log.info(StringFile.SHOW_OBJECT);
            log.log.info(StringFile.SHOW_TYPE);
            log.log.info(StringFile.SHOW_CLASSIFIER);
            log.log.info(StringFile.DELETE_SHIP);
            log.log.info(StringFile.DELETE_OBJECT);
            log.log.info(StringFile.FIND_SHIP);
            log.log.info(StringFile.FIND_OBJECT);
            log.log.info(StringFile.FILTRATION);
            log.log.info("10.Выход");
            switch (EnterDataService.returnInt(11)) {
                case 1:
                    shippingService.viewShipping("select * from shipping");
                    break;
                case 2:
                    shippingService.viewObjects("select * from object");
                    break;
                case 3:
                    Type.TypeEnum.typesShow();
                    break;
                case 4:
                    Classifier.ClassifierEnum.classifiersShow();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    log.log.info("Введите айди перевозки");
                    idShip = EnterDataService.returnInt(90);
                    shippingService.viewShipping("select * from shipping where id ='" + idShip + "'");
                    break;
                case 8:
                    log.log.info("Введите айди объекта");
                    idShip = EnterDataService.returnInt(90);
                    shippingService.viewObjects("select * from object where idobject ='" + idShip + "'");
                    break;
                case 9:
                    shippingService.filtrationShipping();
                    break;
                case 10:
                    return;
                default:
                    log.log.error(StringFile.ERROR);
                    break;
            }
        }
    }
    //выполнено!
}