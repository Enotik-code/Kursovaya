package com.company.enums;

import java.util.ArrayList;
import java.util.List;

public class City {
    public static List<String> cityList = new ArrayList<>();
    public enum CityEnum {
        sofia("София,Болгария"),
        krakov("Краков, Польша"),
        buharest("Бухарест, Румыния"),
        belgrad("Белград, Сербия"),
        budapeht("Будапешт, Венгрия"),
        saraevo("Сараево, Босния и Герцеговина"),
        kiev("Киев, Украина"),
        cheski("Чески-Крумлов, Чехия"),
        varshava("Варшава, Польша"),
        zagreb("Загреб, Хорватия"),
        riga("Рига, Латвия"),
        bratislava("Братислава, Словакия"),
        vilnius("Вильнюс, Литва"),
        stambul("Стамбул, Турция"),
        praha("Прага, Чехия"),
        split("Сплит, Хорватия"),
        piter("Санкт-Петербург, Россия"),
        santorini("Санторини, Греция"),
        luiblyana("Любляна, Словения"),
        tallin("Таллинн, Эстония"),
        tenerive("Тенерифе, Испания"),
        afin("Афины, Греция"),
        lissabon("Лиссабон, Португалия"),
        moscow("Москва, Россия"),
        neapol("Неаполь, Италия");

        private String description;

        CityEnum(String description) {
            this.description = description;

        }

        public static List<String> getListCity(){
            if (!cityList.isEmpty()) cityList.clear();
            for (int i = 0; i < City.CityEnum.values().length; i++) {
                cityList.add(City.CityEnum.values()[i].description);
            }
            return cityList;
        }
    }

}
