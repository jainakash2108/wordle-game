package com.sportradar.wordle.wordlegame.utils;

public class GameRules {
    public static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int WORD_LENGTH = 5;

    public static final String GREEN_BG_COLOR = "\u001B[42m";
    public static final String YELLOW_BG_COLOR = "\u001B[43m";
    public static final String RESET_BG_COLOR = "\u001B[0m";
    public static final String WORD_PATTERN = "[A-Z]+";
    public static final String WORD_SPLIT_PATTERN = "[^A-Za-z]+";
}
