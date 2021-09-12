package com.nabiel.hangman;

public enum GameCategory {
    Animals(0, "Animals"), Sports(1, "Sports");

    private final int index;
    private final String categoryString;

    GameCategory(int index, String categoryString) {
        this.index = index;
        this.categoryString = categoryString;
    }

    public int getIndex() {
        return index;
    }

    public String getCategoryString() {
        return categoryString;
    }
}
