package com.sportradar.wordle.wordlegame;

import java.util.Arrays;
import java.util.Scanner;

import static com.sportradar.wordle.wordlegame.GameRules.FILE_NAME;
import static com.sportradar.wordle.wordlegame.GameRules.GREEN_BG_COLOR;
import static com.sportradar.wordle.wordlegame.GameRules.NUMBER_OF_ATTEMPTS;
import static com.sportradar.wordle.wordlegame.GameRules.RESET_BG_COLOR;
import static com.sportradar.wordle.wordlegame.GameRules.WORD_LENGTH;
import static com.sportradar.wordle.wordlegame.GameRules.WORD_PATTERN;
import static com.sportradar.wordle.wordlegame.GameRules.YELLOW_BG_COLOR;

public class WordleGame {

    private final WordleSolver wordleSolver;
    private final Scanner scanner;

    public WordleGame() {
        this.wordleSolver = new WordleSolver();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        WordleGame game = new WordleGame();
        game.startGame();
    }

    private void startGame() {
        String secretWord = new WordFinder(FILE_NAME).findRandomWordToGuess();

        int guessesLeft = NUMBER_OF_ATTEMPTS;

        gameIntroduction();

        while (guessesLeft > 0) {
            System.out.printf("%nGuesses remaining: %d%n", guessesLeft);

            String guessWord = readGuessWord();
            if (guessWord == null) continue;

            Feedback[] feedback = wordleSolver.solveWordle(secretWord, guessWord);

            printGuessedWordInColor(feedback, guessWord);

            if (isGuessedWordCorrect(feedback)) {
                System.out.printf("%nCongratulations! You've guessed the word: %s%n", secretWord);
                return;
            }

            guessesLeft--;
        }

        System.out.printf("%nGame over! The secret word was: %s%n", secretWord);
    }

    private void gameIntroduction() {
        System.out.println("Welcome to Command-Line Wordle!");
        System.out.printf("Guess the %d letter secret word in %d tries.%n", WORD_LENGTH, NUMBER_OF_ATTEMPTS);
        System.out.println("After each guess, you'll get feedback:");
        System.out.printf(
                """
                            - %sGREEN%s: Letter is correct and in the correct position.
                            - %sYELLOW%s: Letter is correct but in the wrong position.
                            - NO COLOR: Letter is incorrect.
                        """, GREEN_BG_COLOR, RESET_BG_COLOR, YELLOW_BG_COLOR, RESET_BG_COLOR);
        System.out.println("Good luck!");
        System.out.println("-".repeat(50));
    }

    private String readGuessWord() {
        System.out.printf("Enter your %d letter guess word: ", WORD_LENGTH);
        String guess = scanner.nextLine().trim().toUpperCase();

        if (guess.length() != WORD_LENGTH) {
            System.out.printf("Invalid guess. Please enter exactly %d letters. %n", WORD_LENGTH);
            return null;
        }
        if (!guess.matches(WORD_PATTERN)) {
            System.out.println("Invalid guess. Please use only letters A-Z.");
            return null;
        }

        return guess;
    }

    private void printGuessedWordInColor(Feedback[] feedback, String guessWord) {
        for (int index = 0; index < WORD_LENGTH; index++) {
            switch (feedback[index]) {
                case CORRECT_WORD_IN_CORRECT_POSITION:
                    System.out.printf(GREEN_BG_COLOR + guessWord.charAt(index) + RESET_BG_COLOR);
                    continue;
                case CORRECT_WORD_IN_INCORRECT_POSITION:
                    System.out.printf(YELLOW_BG_COLOR + guessWord.charAt(index) + RESET_BG_COLOR);
                    continue;
                case INCORRECT_WORD:
                    System.out.print(guessWord.charAt(index));
            }
        }
        System.out.println();
    }

    private boolean isGuessedWordCorrect(Feedback[] feedback) {
        return Arrays.stream(feedback).allMatch(f -> f == Feedback.CORRECT_WORD_IN_CORRECT_POSITION);
    }
}
