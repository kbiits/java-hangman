package com.nabiel.hangman;

import com.nabiel.data.DataProvider;
import com.nabiel.utils.GameUtil;
import com.nabiel.utils.StdinUtil;
import com.nabiel.utils.Util;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Hangman {
    private DataProvider dataProvider;
    private final StdinUtil stdinUtil;
    private String originalQuestion;

    public Hangman() throws IOException {
        this.stdinUtil = new StdinUtil();
        try {
            this.dataProvider = new DataProvider();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void launch() throws IOException {
        Util.clearConsole();
        GameUtil.printHeader();

        outerLoop:
        while (true) {
            GameCategory category = this.getCategories();

            StringBuilder currentQuestion = this.buildWords(category);
            GameUtil.printTutorial();
            System.out.println();

            int chances = this.getChances();
            while (true) {
                GameUtil.printChance(chances);
                GameUtil.printQuestion(currentQuestion.toString(), category);

                GameOps result = this.guess(currentQuestion);

                if (result == GameOps.WRONG_ANSWER) {
                    --chances;
                    if (chances > 0) // if there's still a chance, print this, else print lose message
                        GameUtil.printJawabanSalah();
                }

                if (result == GameOps.STOP)
                    break outerLoop;
                if (result == GameOps.NEXT)
                    continue outerLoop;
                if (result == GameOps.WON) {
                    GameUtil.wonMessage(this.originalQuestion);
                    break;
                }

                if (chances == 0) {
                    GameUtil.printLoseMessage();
                    System.out.println("Jawaban yang benar adalah : " + this.originalQuestion);
                    break;
                }
            }

            GameUtil.printWantToPlayAgain();
            Character answer = this.stdinUtil.getNextChar();
            Util.clearConsole();
            if (!GameUtil.isWantToPlayAgain(answer)) {
                break;
            }

        }

        this.stdinUtil.close();
        GameUtil.printClosingMessage();
    }

    /**
     * @return StringBuilder
     */
    private StringBuilder buildWords(GameCategory category) throws IOException {
        this.originalQuestion = this.dataProvider.getNextWords(category);
        StringBuilder question =
                new StringBuilder(Util.repeatString(this.originalQuestion.length(), "_"));
        // if there's whitespace for the question, replace all _ with whitespace
        if (this.dataProvider.getCurrentMapWords().containsKey(' ')) {
            this.dataProvider.getCurrentMapWords().get(' ').forEach(
                    index -> question.setCharAt(index, ' '));
        }

        return question;
    }

    private int getChances() {
        if (this.dataProvider.getCurrentMapWords().containsKey(' ')) {
            return this.dataProvider.getCurrentMapWords().size() - 1;
        }
        return this.dataProvider.getCurrentMapWords().size();
    }

    /**
     * @param question StringBuilder
     *
     * @return GameOps
     */
    private GameOps guess(StringBuilder question) {
        GameOps result = GameOps.WRONG_ANSWER;

        System.out.print("Masukkan Jawaban per Karakter : ");
        Character answer = this.stdinUtil.getNextChar();
        System.out.println();

        if (answer == null) {
            throw new NullPointerException();
        }

        // Check if user want to stop or want to skip current words
        if (GameUtil.isWantToStop(answer)) {
            result = GameOps.STOP;
        } else if (GameUtil.isWantToNext(answer)) {
            result = GameOps.NEXT;
        } else if (this.dataProvider.getCurrentMapWords().containsKey(answer)) {
            this.dataProvider.getCurrentMapWords()
                    .get(answer)
                    .forEach(index -> question.setCharAt(index, answer));

            if (question.toString().equals(this.originalQuestion)) {
                result = GameOps.WON;
            } else {
                result = GameOps.RIGHT_ANSWER;
            }
        }

        Util.clearConsole();
        return result;
    }

    private GameCategory getCategories() {
        GameCategory result = null;

        do {
            Util.clearConsole();
            GameUtil.askCategories();
            int categoryChoice = this.stdinUtil.getNextChar() - 49; // if input is '1', subtract it with 49 and we
            // got 0, see ascii table for details
            if (categoryChoice == GameCategory.Animals.getIndex()) {
                result = GameCategory.Animals;
            } else if (categoryChoice == GameCategory.Sports.getIndex()) {
                result = GameCategory.Sports;
            } else {
                System.out.println("Category tidak valid, silahkan masukkan nomor dari category tersebut");
            }
        } while (result == null);

        Util.clearConsole();
        return result;
    }

}
