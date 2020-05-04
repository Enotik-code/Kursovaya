package com.company.enums;

import com.company.logger.Loggers;

public class AdminMenu {
    public enum AdminMenuEnum{
        MANAGE_EMPLOYEE("Управление работниками"),
        MANAGE_ORDER("Управление заказами"),
        MANAGE_USERS("Управление пользователями"),
        EXiT("Выход");

        private final String description;
        AdminMenuEnum(String description) {
            this.description = description;
        }

        public static void adminMenuShow() {
            Loggers loggers = new Loggers(com.company.enums.AdminMenu.class.getName());
            for (int i = 0; i < values().length; i++) {
                loggers.log.info((i + 1) + ". " + values()[i].description);
            }
        }
    }
    }

