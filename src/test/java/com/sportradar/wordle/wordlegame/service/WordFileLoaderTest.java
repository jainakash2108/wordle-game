package com.sportradar.wordle.wordlegame.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordFileLoaderTest {

    private final WordFileLoader loader = new WordFileLoader();

    @Test
    void testLoadWords_successfullyLoadsWordsFromFile() {
        ReflectionTestUtils.setField(loader, "fileName", "test-words.txt");

        Set<String> words = loader.loadWords();

        assertNotNull(words);
        assertEquals(3, words.size());
        assertTrue(words.contains("apple"));
        assertTrue(words.contains("banana"));
        assertTrue(words.contains("grape"));
    }

    @Test
    void testLoadWords_throwsExceptionWhenFileNotFound() {
        ReflectionTestUtils.setField(loader, "fileName", "non-existent-file.txt");

        Exception exception = assertThrows(IllegalArgumentException.class, loader::loadWords);

        assertTrue(exception.getMessage().contains("Word file not found on classpath"));
    }
}
