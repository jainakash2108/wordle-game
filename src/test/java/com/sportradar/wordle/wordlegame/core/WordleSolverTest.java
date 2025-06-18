package com.sportradar.wordle.wordlegame.core;

import org.junit.jupiter.api.Test;

import static com.sportradar.wordle.wordlegame.core.Feedback.CORRECT_WORD_IN_CORRECT_POSITION;
import static com.sportradar.wordle.wordlegame.core.Feedback.CORRECT_WORD_IN_INCORRECT_POSITION;
import static com.sportradar.wordle.wordlegame.core.Feedback.INCORRECT_WORD;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WordleSolverTest {

    private final WordleSolver solver = new WordleSolver();

    @Test
    void test_all_letters_are_in_correct_positions() {
        Feedback[] expected = {
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION
        };
        assertArrayEquals(expected, solver.solveWordle("HELLO", "HELLO"));
    }

    @Test
    void test_all_letters_are_in_incorrect_positions() {
        Feedback[] expected = {
                CORRECT_WORD_IN_INCORRECT_POSITION,
                CORRECT_WORD_IN_INCORRECT_POSITION,
                CORRECT_WORD_IN_INCORRECT_POSITION,
                CORRECT_WORD_IN_INCORRECT_POSITION,
                CORRECT_WORD_IN_INCORRECT_POSITION
        };
        assertArrayEquals(expected, solver.solveWordle("JIGHF", "FGHIJ"));
    }

    @Test
    void test_all_letters_are_incorrect() {
        Feedback[] expected = {
                INCORRECT_WORD,
                INCORRECT_WORD,
                INCORRECT_WORD,
                INCORRECT_WORD,
                INCORRECT_WORD
        };
        assertArrayEquals(expected, solver.solveWordle("ABCDE", "FGHIJ"));
    }

    @Test
    void test_yellow_highlight() {
        Feedback[] expected = {
                CORRECT_WORD_IN_INCORRECT_POSITION,
                CORRECT_WORD_IN_INCORRECT_POSITION,
                INCORRECT_WORD,
                INCORRECT_WORD,
                INCORRECT_WORD
        };
        assertArrayEquals(expected, solver.solveWordle("APPLE", "PLUMB"));
    }

    @Test
    void test_no_yellow_highlight_for_excess_letters() {
        Feedback[] expected = {
                INCORRECT_WORD,
                INCORRECT_WORD,
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION,
                CORRECT_WORD_IN_CORRECT_POSITION
        };
        assertArrayEquals(expected, solver.solveWordle("WATER", "OTTER"));
    }

    @Test
    void test_illegal_argument_for_length() {
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle("WORD", "GUESS"), "Correct word length does not match guess word length!");
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle("WORDLE", "GUESS"), "Correct word length does not match guess word length!");
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle("WORD", "GUESSS"), "Correct word length does not match guess word length!");
    }

    @Test
    void test_illegal_argument_for_null() {
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle(null, "GUESS"), "Correct word length does not match guess word length!");
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle("WORDL", null), "Correct word length does not match guess word length!");
        assertThrows(IllegalArgumentException.class, () -> solver.solveWordle(null, null), "Correct word length does not match guess word length!");
    }

}