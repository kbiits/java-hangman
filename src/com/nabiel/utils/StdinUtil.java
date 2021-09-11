package com.nabiel.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class StdinUtil {

    final private InputStreamReader inputStreamReader;

    public StdinUtil() {
        this.inputStreamReader = new InputStreamReader(System.in);
    }

    public Character getNextChar() {
        Character returnValue = null;
        try {
            char temp;
            String tempStr = "";
            // Check if characters is special chars
            while ((temp = (char) this.inputStreamReader.read()) != '\n') {
                tempStr += temp;
            }
            if (tempStr.length() > 0)
                returnValue = (char) tempStr.charAt(0);
            else
                returnValue = 19;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(returnValue);
        return Character.toUpperCase(returnValue);
    }

    public void close() {
        try {
            this.inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
