package com.company.enums;

import ch.qos.logback.core.subst.Token;
import com.company.bean.Shipping;
import com.company.logger.Loggers;

import java.util.ArrayList;
import java.util.List;

public class Type extends Shipping {
    public static List<String> typeList = new ArrayList<>();
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

        public static List<String> returnListType() {
            if (!typeList.isEmpty()) typeList.clear();
            for (int i = 0; i < Type.TypeEnum.values().length; i++) {
                typeList.add(TypeEnum.values()[i].id + ". "+ Type.TypeEnum.values()[i].description);
            }
            return typeList;
        }
    }
}
