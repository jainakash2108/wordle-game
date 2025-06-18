package com.sportradar.wordle.wordlegame.service;

import com.sportradar.wordle.wordlegame.core.WordFilter;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class WordFinderService {

    private final WordFileLoader wordFileLoader;
    private final WordFilter wordFilter;

    private Set<String> words;

    public WordFinderService(WordFileLoader wordFileLoader, WordFilter wordFilter) {
        this.wordFileLoader = wordFileLoader;
        this.wordFilter = wordFilter;
    }

    @PostConstruct
    public void initialize() {
        Set<String> rawWords = wordFileLoader.loadWords();
        this.words = wordFilter.filterValidWords(rawWords);
    }

    public String findRandomWordToGuess() {
        if (words.isEmpty()) {
            throw new IllegalStateException("No valid words found in file");
        }
        return words.stream()
                .skip(ThreadLocalRandom.current().nextInt(words.size()))
                .findFirst()
                .orElseThrow();
    }
}
