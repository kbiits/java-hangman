package com.nabiel.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nabiel.hangman.GameCategory;

public class DataProvider {
    final private static String FILE_PATH = "src/com/nabiel/data/dictionary.txt";
    final private long numOfLines;
    private final Map<Character, List<Integer>> currentMapWords;

    public DataProvider() throws IOException {
        this.numOfLines = this.countLines();
        this.currentMapWords = new HashMap<>();
    }

    public Map<Character, List<Integer>> getCurrentMapWords() {
        return currentMapWords;
    }

    public long countLines() throws IOException {
        long countLines = 0;

        FileInputStream fileInputStream = new FileInputStream(DataProvider.FILE_PATH);
        int numOfCharsRead;
        byte[] readBytes = new byte[1024]; // 1kb
        while ((numOfCharsRead = fileInputStream.read(readBytes)) != -1) { // while not EOF
            for (int i = 0; i < numOfCharsRead; i++) { // count line char
                if (readBytes[i] == '\n')
                    countLines++;
            }

            if (readBytes[numOfCharsRead - 1] != '\n')
                countLines++;
        }
        fileInputStream.close();

        return countLines;
    }

    public String getNextWords(GameCategory category) throws IOException {
        this.currentMapWords.clear(); // clear the hash map for next words
        int randLine = (int) (Math.random() * this.numOfLines) + 2; // + 2 because the first line is category
        return this.getNthLineFromFile(randLine, category);
    }

    private String getNthLineFromFile(int nthLine, GameCategory category) throws IOException {
        String word = this.readLine(nthLine); // word used to store current line string

        String[] arrWords = word.trim().split(",");
        word = arrWords[category.getIndex()].trim();

        // save current words to map
        for (int i = 0; i < word.length(); i++) {
            if (this.currentMapWords.containsKey(word.charAt(i))) {
                this.currentMapWords.get(word.charAt(i)).add(i);
            } else {
                List<Integer> temp = new ArrayList<>();
                temp.add(i);
                this.currentMapWords.put(word.charAt(i), temp);
            }
        }

        return word;
    }

    private String readLine(int nthLine) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(DataProvider.FILE_PATH);
        String word = "";

        int numOfCharsRead; // number of chars readied by the fileInputStream
        int currentLineNumber = 1;
        byte[] readBytes = new byte[1024]; // 1kb array

        while ((numOfCharsRead = fileInputStream.read(readBytes)) != -1) { // read 1kb data (1024 / 2 chars))
            for (int i = 0; i < numOfCharsRead; i++) {
                // stop if already get the line
                if (currentLineNumber > nthLine)
                    break;

                // continue if not in current line and not LF char
                if (currentLineNumber != nthLine && readBytes[i] != '\n') {
                    continue;
                }

                // if in the desired line, store all chars to word variable
                if (currentLineNumber == nthLine && (readBytes[i] != '\n' && readBytes[i] != '\r')) {
                    char currentChar = Character.toUpperCase((char) readBytes[i]);

                    word += currentChar;
                    continue;
                }

                if (readBytes[i] == '\n')
                    currentLineNumber++;
            }
        }

        fileInputStream.close();
        return word;
    }

}
