package com.sportradar.wordle.wordlegame.service;

import com.sportradar.wordle.wordlegame.core.Feedback;
import com.sportradar.wordle.wordlegame.core.WordleSolver;
import com.sportradar.wordle.wordlegame.ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static com.sportradar.wordle.wordlegame.utils.GameRules.NUMBER_OF_ATTEMPTS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WordleGameServiceTest {

    private WordFinderService wordFinderService;
    private WordleSolver wordleSolver;
    private ConsoleUI consoleUI;
    private WordleGameService gameService;

    @BeforeEach
    void setUp() {
        wordFinderService = mock(WordFinderService.class);
        wordleSolver = mock(WordleSolver.class);
        consoleUI = mock(ConsoleUI.class);
        gameService = new WordleGameService(wordFinderService, wordleSolver, consoleUI);
    }

    @Test
    void startGame_correctGuess_endsEarly() {
        String secretWord = "APPLE";
        when(wordFinderService.findRandomWordToGuess()).thenReturn(secretWord);

        when(consoleUI.readGuessWord()).thenReturn("APPLE");

        Feedback[] feedback = new Feedback[secretWord.length()];
        for (int i = 0; i < secretWord.length(); i++) {
            feedback[i] = Feedback.CORRECT_WORD_IN_CORRECT_POSITION;
        }
        when(wordleSolver.solveWordle(secretWord, "APPLE")).thenReturn(feedback);

        gameService.startGame();

        InOrder inOrder = inOrder(consoleUI);
        inOrder.verify(consoleUI).showIntroduction();
        inOrder.verify(consoleUI).printRemainingGuesses(NUMBER_OF_ATTEMPTS);
        inOrder.verify(consoleUI).readGuessWord();
        inOrder.verify(consoleUI).printFeedback(feedback, "APPLE");
        inOrder.verify(consoleUI).showSuccess(secretWord);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void startGame_nullGuess_skipsTurnAndContinues() {
        String secretWord = "APPLE";
        when(wordFinderService.findRandomWordToGuess()).thenReturn(secretWord);

        when(consoleUI.readGuessWord())
                .thenReturn(null)
                .thenReturn("APPLE");

        Feedback[] feedback = new Feedback[secretWord.length()];
        for (int i = 0; i < secretWord.length(); i++) {
            feedback[i] = Feedback.CORRECT_WORD_IN_CORRECT_POSITION;
        }
        when(wordleSolver.solveWordle(secretWord, "APPLE")).thenReturn(feedback);

        gameService.startGame();

        verify(consoleUI, times(1)).showIntroduction();
        verify(consoleUI, times(2)).printRemainingGuesses(anyInt());
        verify(consoleUI, times(2)).readGuessWord();
        verify(consoleUI).printFeedback(feedback, "APPLE");
        verify(consoleUI).showSuccess(secretWord);
    }

    @Test
    void startGame_exhaustGuesses_showsGameOver() {
        String secretWord = "APPLE";
        when(wordFinderService.findRandomWordToGuess()).thenReturn(secretWord);

        when(consoleUI.readGuessWord()).thenReturn("WRONG");

        Feedback[] feedback = new Feedback[secretWord.length()];
        for (int i = 0; i < secretWord.length(); i++) {
            feedback[i] = Feedback.INCORRECT_WORD;
        }
        when(wordleSolver.solveWordle(secretWord, "WRONG")).thenReturn(feedback);

        gameService.startGame();

        verify(consoleUI).showIntroduction();
        verify(consoleUI, times(NUMBER_OF_ATTEMPTS)).printRemainingGuesses(anyInt());
        verify(consoleUI, times(NUMBER_OF_ATTEMPTS)).readGuessWord();
        verify(consoleUI, times(NUMBER_OF_ATTEMPTS)).printFeedback(feedback, "WRONG");
        verify(consoleUI).showGameOver(secretWord);
        verify(consoleUI, never()).showSuccess(any());
    }
}
