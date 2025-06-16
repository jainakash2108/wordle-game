package com.sportradar.wordle.wordlegame;

public class GameRules {
    static final int NUMBER_OF_ATTEMPTS = 5;
    static final int WORD_LENGTH = 5;

    static final String GREEN_BG_COLOR = "\u001B[42m";
    static final String YELLOW_BG_COLOR = "\u001B[43m";
    static final String RESET_BG_COLOR = "\u001B[0m";
    static final String FILE_NAME = "words.txt";
    static final String WORD_PATTERN = "[A-Z]+";
    static final String WORD_SPLIT_PATTERN = "[^A-Za-z]+";
}
