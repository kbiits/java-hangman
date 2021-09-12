package com.nabiel.utils;

import com.nabiel.hangman.GameCategory;

public class GameUtil {

    final public static int STOP_CHAR = 24; // Ctrl + X
    final public static int NEXT_CHAR = 14; // Ctrl + N
    final public static int PLAY_AGAIN_CHAR = 'Y';

    public static void printHeader() {
        System.out.print(Util.repeatString(10, "-"));
        System.out.print(" Hangman Game ");
        System.out.println(Util.repeatString(10, "-"));
        System.out.println();
    }

    public static void printTutorial() {
        System.out.println("\nBantuan : ");
        System.out.println("Tekan `CTRL + N` untuk mendapatkan random words lainnya");
        System.out.println("Tekan `CTRL + X` untuk keluar");
    }

    public static void printClosingMessage() {
        System.out.println("Bye, Sampai ketemu lagi");
    }

    public static void printQuestion(String question, GameCategory category) {
        System.out.printf("%-15s : Ada %d karakter\n", "Hint", question.length());
        System.out.printf("%-15s : %s\n", "Topik", category.getCategoryString());
        System.out.printf("%-15s : %s\n", "Tebak ini", question);
    }

    public static void askCategories() {
        System.out.println("Kategori : ");
        System.out.println("1. Animals");
        System.out.println("2. Sports");
        System.out.print("Pilih Kategori : ");
    }

    public static void wonMessage(String jawaban) {
        System.out.println("Selamat!!! Kamu menang");
        System.out.println("Jawaban : " + jawaban);
        System.out.println();
    }

    public static void printChance(int chance) {
        System.out.printf("Anda memiliki %d Kesempatan\n", chance);
    }

    public static void printLoseMessage() {
        System.out.println("Yahh.... kamu kalah ");
    }

    public static void printWantToPlayAgain() {
        System.out.print("Ingin bermain lagi ? (Y/n) : ");
    }


    public static boolean isWantToStop(Character character) {
        return character == GameUtil.STOP_CHAR;
    }

    public static boolean isWantToNext(Character character) {
        return character == GameUtil.NEXT_CHAR;
    }

    public static boolean isWantToPlayAgain(Character character) {
        return Character.toUpperCase(character) == GameUtil.PLAY_AGAIN_CHAR;
    }

    public static void printJawabanSalah() {
        System.out.println("Jawaban Salah :( ... Coba Lagi!!!");
    }
}
