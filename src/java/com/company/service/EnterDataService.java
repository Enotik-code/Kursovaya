package com.company.service;

import com.company.logger.Loggers;
import com.company.string.StringFile;

import java.util.Scanner;

public class EnterDataService {

    static Scanner in = new Scanner(System.in);
    static Scanner inString = new Scanner(System.in);
    static Loggers log = new Loggers(com.company.service.EnterDataService.class.getName());
    static int dataInt;
    static String dataString;

    public static int returnInt(int radix) {
        while (!in.hasNextInt(radix)) {
            log.log.error(StringFile.THIS_NOT_A_NUMBER);
            in.next();
        }
        dataInt = in.nextInt();
        return dataInt;
    }

    public static String returnString() {
        dataString = inString.nextLine();
        return dataString;
    }

    public static boolean returnBoolean(){
        System.out.println("1. TRUE\n2. FALSE");
        if(returnInt(2) == 1) return true;
        else return false;
    }
}
