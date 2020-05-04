package com.company.enums;

import com.company.logger.Loggers;

public class EmployeeMenu {
    public enum EmployeeMenuEnum{
        SHOW_REVIEWS("Просмотреть отзывы"),
        SHOW_YOUR_DATA("Просмотреть информацию"),
        EXiT("Выход");

        private final String description;

        EmployeeMenuEnum(String description) {
            this.description = description;
        }

        public static void employeeMenuShow() {
            Loggers loggers = new Loggers(com.company.enums.EmployeeMenu.class.getName());
            for (int i = 0; i < values().length; i++) {
                loggers.log.info((i + 1) + ". " + values()[i].description);
            }
        }
    }
}
