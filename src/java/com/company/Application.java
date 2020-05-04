package com.company;


import static com.company.service.MenuService.userService;
import static com.company.service.RegistrationService.menuService;

public class Application {
    public static void main(String[] args) {
        menuService.registrationMenu();
        //userService.makeShipping(51);
        //userService.viewShipping("select * from shipping");
    }
}
