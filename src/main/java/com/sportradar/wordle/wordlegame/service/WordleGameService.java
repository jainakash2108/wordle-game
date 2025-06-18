package com.sportradar.wordle.wordlegame.service;

import com.sportradar.wordle.wordlegame.core.Feedback;
import com.sportradar.wordle.wordlegame.core.WordleSolver;
import com.sportradar.wordle.wordlegame.ui.ConsoleUI;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.sportradar.wordle.wordlegame.core.Feedback.CORRECT_WORD_IN_CORRECT_POSITION;
import static com.sportradar.wordle.wordlegame.utils.GameRules.NUMBER_OF_ATTEMPTS;

@Component
public class WordleGameService {

    private final WordFinderService wordFinderService;
    private final WordleSolver wordleSolver;
    private final ConsoleUI consoleUI;

    public WordleGameService(WordFinderService wordFinderService, WordleSolver wordleSolver, ConsoleUI consoleUI) {
        this.wordFinderService = wordFinderService;
        this.wordleSolver = wordleSolver;
        this.consoleUI = consoleUI;
    }

    public void startGame() {
        String secretWord = wordFinderService.findRandomWordToGuess();
        int guessesLeft = NUMBER_OF_ATTEMPTS;

        consoleUI.showIntroduction();

        while (guessesLeft > 0) {
            consoleUI.printRemainingGuesses(guessesLeft);
            String guessWord = consoleUI.readGuessWord();

            if (guessWord == null) continue;

            Feedback[] feedback = wordleSolver.solveWordle(secretWord, guessWord);
            consoleUI.printFeedback(feedback, guessWord);

            if (isCorrectGuess(feedback)) {
                consoleUI.showSuccess(secretWord);
                return;
            }

            guessesLeft--;
        }

        consoleUI.showGameOver(secretWord);
    }

    private static boolean isCorrectGuess(Feedback[] feedback) {
        return Arrays.stream(feedback)
                .allMatch(f -> f == CORRECT_WORD_IN_CORRECT_POSITION);
    }
}

