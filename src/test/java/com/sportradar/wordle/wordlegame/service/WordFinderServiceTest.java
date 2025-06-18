package com.sportradar.wordle.wordlegame.service;

import com.sportradar.wordle.wordlegame.core.WordFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WordFinderServiceTest {

    private WordFileLoader wordFileLoader;
    private WordFilter wordFilter;
    private WordFinderService wordFinderService;

    @BeforeEach
    void setUp() {
        wordFileLoader = mock(WordFileLoader.class);
        wordFilter = mock(WordFilter.class);
        wordFinderService = new WordFinderService(wordFileLoader, wordFilter);
    }

    @Test
    void testFindRandomWordToGuess_returnsValidWord() {
        Set<String> rawWords = Set.of("word1", "word2", "word3");
        Set<String> filteredWords = Set.of("WORD1", "WORD2", "WORD3");

        when(wordFileLoader.loadWords()).thenReturn(rawWords);
        when(wordFilter.filterValidWords(rawWords)).thenReturn(filteredWords);

        wordFinderService.initialize();
        String randomWord = wordFinderService.findRandomWordToGuess();

        assertNotNull(randomWord);
        assertTrue(filteredWords.contains(randomWord));
    }

    @Test
    void testFindRandomWordToGuess_throwsWhenNoWords() {
        when(wordFileLoader.loadWords()).thenReturn(Set.of());
        when(wordFilter.filterValidWords(Set.of())).thenReturn(Set.of());

        wordFinderService.initialize();

        Exception exception = assertThrows(IllegalStateException.class, () -> wordFinderService.findRandomWordToGuess());
        assertEquals("No valid words found in file", exception.getMessage());
    }
}
