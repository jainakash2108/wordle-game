package com.sportradar.wordle.wordlegame.service;

import com.sportradar.wordle.wordlegame.ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsoleUITest {

    private Scanner mockScanner;
    private ConsoleUI consoleUI;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        consoleUI = new ConsoleUI(mockScanner);
    }

    @Test
    void testReadGuessWord_validInput() {
        when(mockScanner.nextLine()).thenReturn("apple");

        String guess = consoleUI.readGuessWord();

        assertEquals("APPLE", guess);
        verify(mockScanner, times(1)).nextLine();
    }

    @Test
    void testReadGuessWord_invalidLength() {
        when(mockScanner.nextLine()).thenReturn("app");

        String guess = consoleUI.readGuessWord();

        assertNull(guess);
        verify(mockScanner, times(1)).nextLine();
    }

    @Test
    void testReadGuessWord_invalidCharacters() {
        when(mockScanner.nextLine()).thenReturn("app1e");

        String guess = consoleUI.readGuessWord();

        assertNull(guess);
        verify(mockScanner, times(1)).nextLine();
    }

    @Test
    void testPrintRemainingGuesses() {
        consoleUI.printRemainingGuesses(3);
    }

    @Test
    void testShowIntroduction() {
        consoleUI.showIntroduction();
    }

    @Test
    void testShowSuccess() {
        consoleUI.showSuccess("APPLE");
    }

    @Test
    void testShowGameOver() {
        consoleUI.showGameOver("APPLE");
    }
}
