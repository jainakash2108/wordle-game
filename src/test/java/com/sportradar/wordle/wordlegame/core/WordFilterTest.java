package com.sportradar.wordle.wordlegame.core;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordFilterTest {

    private final WordFilter wordFilter = new WordFilter();

    @Test
    void testFilterValidWords_removesInvalidLengthAndCharacters() {
        Set<String> input = Set.of("apple", "ban@na", "grape", "kiwi", "P1zza", "MANGO", "lemonade");
        Set<String> expected = Set.of("APPLE", "GRAPE", "MANGO");

        Set<String> actual = wordFilter.filterValidWords(input);

        assertEquals(expected, actual);
    }

    @Test
    void testFilterValidWords_handlesMixedCaseAndWhitespace() {
        Set<String> input = Set.of("  Apple ", "\tgrape\n", "  MANGO  ");
        Set<String> expected = Set.of("APPLE", "GRAPE", "MANGO");

        Set<String> actual = wordFilter.filterValidWords(input);

        assertEquals(expected, actual);
    }

    @Test
    void testFilterValidWords_handlesWordSeparation() {
        Set<String> input = Set.of("apple,grape;melon", "MANGO BANANA", "cherry");
        Set<String> expected = Set.of("APPLE", "GRAPE", "MELON", "MANGO");

        Set<String> actual = wordFilter.filterValidWords(input);

        assertEquals(expected, actual);
    }

    @Test
    void testFilterValidWords_returnsEmptySetOnNoValidWords() {
        Set<String> input = Set.of("123", "!!", "abcdefg", "  ");
        Set<String> actual = wordFilter.filterValidWords(input);

        assertTrue(actual.isEmpty());
    }

    @Test
    void testFilterValidWords_allValidWords() {
        Set<String> input = Set.of("Apple", "Mango", "Lemon");
        Set<String> expected = Set.of("APPLE", "MANGO", "LEMON");

        Set<String> actual = wordFilter.filterValidWords(input);

        assertEquals(expected, actual);
    }
}
