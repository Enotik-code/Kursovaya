package com.company.enums;

import com.company.logger.Loggers;

import java.util.ArrayList;
import java.util.List;

public class PositionEnumClass {
    public static List<String> positionList = new ArrayList<>();
    public enum PositionEnum {
        driver(1, "Водитель"),
        manager(2, "Менеджер"),
        logist(3, "Логист"),
        ecomomist(4, "Экономист"),
        urist(5, "Юрист"),
        director(6, "Директор");

        private final String description;
        private int id;

        PositionEnum(int id, String description) {
            this.description = description;
            this.id = id;
        }

        public static List<String> PositionShow() {
            if(positionList.isEmpty()) positionList.clear();
            for (int i = 0; i < values().length; i++) {
                positionList.add(PositionEnum.values()[i].id + ". " + PositionEnum.values()[i].description);
            }
            return positionList;
        }
    }
}
