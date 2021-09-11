package com.nabiel.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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

    public String getNextWords() throws IOException {
        this.currentMapWords.clear(); // clear the hash map for next words
        int randLine = (int) (Math.random() * this.numOfLines) + 1;
        return this.getNthLineFromFile(randLine);
    }

    private String getNthLineFromFile(int nthLine) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(DataProvider.FILE_PATH);
        String word = ""; // word used to store current line string

        int numOfCharsRead; // number of chars readied by the fileInputStream
        int currentLineNumber = 1;
        byte[] readBytes = new byte[1024]; // read 1kb data (1024 / 2 chars))

        while ((numOfCharsRead = fileInputStream.read(readBytes)) != -1) {
            int tempCounter = 0; // used to find index in the line (not the index in readBytes array)
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
                    // Save char to map
                    if (this.currentMapWords.containsKey(currentChar)) {
                        this.currentMapWords.get(currentChar).add(tempCounter);
                    }
                    else {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(tempCounter);
                        this.currentMapWords.put(currentChar, temp);
                    }

                    tempCounter++;
                    continue;
                }

                if (readBytes[i] == '\n')
                    currentLineNumber++;
            }
        }

        System.out.println(this.currentMapWords.toString());
        return word;
    }

}
