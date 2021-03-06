package com.company.enums;


import com.company.bean.Shipping;
import com.company.logger.Loggers;

import java.util.ArrayList;
import java.util.List;

public class Classifier extends Shipping {
    public static List<String> classifierList = new ArrayList<>();
    public enum ClassifierEnum  {
        industrialGoods(0, "Перевозки промышленных товаров"),
        constructionCargo(1, "Перевозки строительных грузов"),
        agriculturalGoods(2, "Перевозки сельскохозяйственных товаров"),
        mailTransportation(3, "Перевозка почты"),
        dangerousGoods(4, "Первозка опасных грузов");

        private String description;
        private int id;

        ClassifierEnum(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public static void classifiersShow() {
            Loggers loggers = new Loggers(com.company.enums.Classifier.class.getName());
            for (int i = 0; i < ClassifierEnum.values().length; i++) {
                loggers.log.info((ClassifierEnum.values()[i].id + ". "
                        + ClassifierEnum.values()[i].description));
            }
        }

        public static List<String> returnListClassifier() {
            if (!classifierList.isEmpty()) classifierList.clear();
            for (int i = 0; i < ClassifierEnum.values().length; i++) {
                classifierList.add(ClassifierEnum.values()[i].id + ". " +ClassifierEnum.values()[i].description);
            }
            return classifierList;
        }
    }
}
