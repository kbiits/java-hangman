package com.nabiel.utils;

public class Util {
    public static String repeatString(int nTimes, String str) {
        String newString = new String("");
        for (int i = 0; i < nTimes; i++) {
            newString = newString + str;
        }

        return newString;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
