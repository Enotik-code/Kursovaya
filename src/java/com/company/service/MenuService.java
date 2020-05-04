package com.company.service;

import com.company.enums.AdminMenu;
import com.company.enums.EmployeeMenu;
import com.company.enums.MainMenu;
import com.company.enums.UserMenu;
import com.company.logger.Loggers;
import com.company.string.StringFile;

import java.sql.SQLException;

public class MenuService {
    static Loggers log = new Loggers(com.company.service.MenuService.class.getName());
    public static UserService userService = new UserService();
    public static EmployeeService employeeService= new EmployeeService();
    public static RegistrationService registrationService= new RegistrationService();
    public static AdminService adminService= new AdminService();
    public static ShippingService shippingService = new ShippingService();
    int choise = 0;

    public void registrationMenu() {
        while (true) {
            MainMenu.MainMenuEnum.MenuShow();
            log.log.info(StringFile.UR_CHOISE);
            choise = EnterDataService.returnInt(4);
            switch (choise) {
                case 1:
                    registrationService.login();
                    break;
                case 2:
                    registrationService.registrationConsole();
                    break;
                case 3:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    return;
            }
        }
    }

    public void userMenu(String email, int id){
        while (true) {
            UserMenu.UserMenuEnum.userMenuShow();
            log.log.info(StringFile.UR_CHOISE);
            choise = EnterDataService.returnInt(6);
            switch (choise) {
                case 1:
                    break;
                case 2:
                    userService.makeReview(id);
                    break;
                case 3:
                    userService.getUser(email);
                    break;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    return;
            }
        }
    }

    public void employeeMenu(String email, int id){
        while (true) {
            EmployeeMenu.EmployeeMenuEnum.employeeMenuShow();
            log.log.info(StringFile.UR_CHOISE);
            choise = EnterDataService.returnInt(4);
            switch (choise) {
                case 1:
                    employeeService.getReviewsForOneEmployee(id);
                    break;
                case 2:
                    employeeService.getInfo(email, id);
                    break;
                case 3:
                    return;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    break;
            }
        }
    };

    public void adminMenu(String email){
        while (true) {
            AdminMenu.AdminMenuEnum.adminMenuShow();
            log.log.info(StringFile.UR_CHOISE);
            choise = EnterDataService.returnInt(5);
            switch (choise) {
                case 1:
                    try {
                        adminService.manageEmployee();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case 2:
                    adminService.manageShipping();
                    break;
                case 3:
                    adminService.manageUser();
                    break;
                default:
                    log.log.error(StringFile.THIS_NOT_A_NUMBER);
                    return;
            }
        }
    };
}
