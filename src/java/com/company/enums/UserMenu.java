package com.company.enums;

import com.company.logger.Loggers;

public class UserMenu {
    public enum UserMenuEnum {
        MAKE_ORDER("Сделать заказ"),
        MAKE_REVIEW("Оставить отзыв"),
        SHOW_YOUR_DATA("Ваши данные"),
        SHOW_YOUR_ORDERS("Ваши заказы"),
        EXiT("Выход");

        private final String description;

        UserMenuEnum(String description) {
            this.description = description;
        }

        public static void userMenuShow() {
            Loggers loggers = new Loggers(com.company.enums.UserMenu.class.getName());
            for (int i = 0; i < values().length; i++) {
                loggers.log.info((i + 1) + ". " + values()[i].description);
            }
        }
    }
}

