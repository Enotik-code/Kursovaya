package com.company.enums;

import com.company.logger.Loggers;

public class MainMenu {
    public enum MainMenuEnum {
        LOGIN("Войти"),
        REGISTRATION("Регистрация"),
        EXiT("Выход");

        private final String description;

        MainMenuEnum(String description) {
            this.description = description;
        }

        public static void MenuShow() {
            Loggers loggers = new Loggers(com.company.enums.MainMenu.class.getName());
            for (int i = 0; i < values().length; i++) {
                loggers.log.info((i + 1) + ". " + values()[i].description);
            }
        }
    }
}

