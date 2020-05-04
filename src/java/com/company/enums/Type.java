package com.company.enums;

import com.company.bean.Shipping;
import com.company.logger.Loggers;

public class Type extends Shipping {
    public enum TypeEnum {
        intercityTransportation(0, "Внутригородские перевозки"),
        interregionalTransportation(1, "Межрегиональные перевозки"),
        internationalTransportation(2, "Международные перевозки"),
        InterurbanTransportation(3, "Междугородние перевозки");

        private String description;
        private int id;
        ;

        TypeEnum(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public static void typesShow() {
            Loggers loggers = new Loggers(com.company.enums.Type.class.getName());
            for (int i = 0; i < values().length; i++) {
                loggers.log.info((values()[i].id + ". " + values()[i].description));
            }
        }
    }
}
