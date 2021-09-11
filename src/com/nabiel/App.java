package com.nabiel;

import com.nabiel.hangman.Hangman;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Hangman hangman = new Hangman();
        hangman.launch();
    }
}
