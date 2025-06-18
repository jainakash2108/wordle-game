package com.sportradar.wordle.wordlegame.ui;

import com.sportradar.wordle.wordlegame.core.Feedback;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.sportradar.wordle.wordlegame.utils.GameRules.GREEN_BG_COLOR;
import static com.sportradar.wordle.wordlegame.utils.GameRules.RESET_BG_COLOR;
import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_LENGTH;
import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_PATTERN;
import static com.sportradar.wordle.wordlegame.utils.GameRules.YELLOW_BG_COLOR;

@Component
public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showIntroduction() {
        System.out.println("Welcome to Command-Line Wordle!");
        System.out.printf("Guess the %d letter secret word in %d tries.%n", WORD_LENGTH, 5);
        System.out.println("After each guess, you'll get feedback:");
        System.out.printf("""
                - %sGREEN%s: Letter is correct and in the correct position.
                - %sYELLOW%s: Letter is correct but in the wrong position.
                - NO COLOR: Letter is incorrect.
                """, GREEN_BG_COLOR, RESET_BG_COLOR, YELLOW_BG_COLOR, RESET_BG_COLOR);
        System.out.println("Good luck!");
        System.out.println("-".repeat(50));
    }

    public void printRemainingGuesses(int guessesLeft) {
        System.out.printf("%nGuesses remaining: %d%n", guessesLeft);
    }

    public String readGuessWord() {
        System.out.printf("Enter your %d letter guess word: ", WORD_LENGTH);
        String guess = scanner.nextLine().trim().toUpperCase();

        if (guess.length() != WORD_LENGTH) {
            System.out.printf("Invalid guess. Please enter exactly %d letters.%n", WORD_LENGTH);
            return null;
        }

        if (!guess.matches(WORD_PATTERN)) {
            System.out.println("Invalid guess. Please use only letters A-Z.");
            return null;
        }

        return guess;
    }

    public void printFeedback(Feedback[] feedback, String guessWord) {
        for (int i = 0; i < WORD_LENGTH; i++) {
            switch (feedback[i]) {
                case CORRECT_WORD_IN_CORRECT_POSITION ->
                        System.out.printf(GREEN_BG_COLOR + guessWord.charAt(i) + RESET_BG_COLOR);
                case CORRECT_WORD_IN_INCORRECT_POSITION ->
                        System.out.printf(YELLOW_BG_COLOR + guessWord.charAt(i) + RESET_BG_COLOR);
                case INCORRECT_WORD -> System.out.print(guessWord.charAt(i));
            }
        }
        System.out.println();
    }

    public void showSuccess(String secretWord) {
        System.out.printf("%nCongratulations! You've guessed the word: %s%n", secretWord);
    }

    public void showGameOver(String secretWord) {
        System.out.printf("%nGame over! The secret word was: %s%n", secretWord);
    }
}


