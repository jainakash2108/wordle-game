package com.sportradar.wordle.wordlegame;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sportradar.wordle.wordlegame.GameRules.WORD_LENGTH;
import static com.sportradar.wordle.wordlegame.GameRules.WORD_PATTERN;
import static com.sportradar.wordle.wordlegame.GameRules.WORD_SPLIT_PATTERN;

public class WordFinder {

    private final String fileName;
    private final Set<String> words;

    public WordFinder(String fileName) {
        this.fileName = Objects.requireNonNull(fileName, "File name cannot be null");
        this.words = loadWords();
    }

    public String findRandomWordToGuess() {
        if (words.isEmpty()) {
            throw new IllegalStateException("No valid words found in file: " + fileName);
        }
        return words.stream()
                .skip(ThreadLocalRandom.current().nextInt(words.size()))
                .findFirst()
                .orElseThrow();
    }

    private Set<String> loadWords() {
        URL fileLocation = getClass().getClassLoader().getResource(fileName);
        if (fileLocation == null) {
            throw new IllegalArgumentException("Word file not found on classpath: " + fileName);
        }

        Path path;
        try {
            path = Paths.get(fileLocation.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for file: " + fileName, e);
        }

        if (!Files.exists(path)) {
            throw new IllegalStateException("Word file does not exist: " + path);
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .flatMap(Pattern.compile(WORD_SPLIT_PATTERN)::splitAsStream)
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .filter(word -> word.length() == WORD_LENGTH && word.matches(WORD_PATTERN))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read word file: " + path, e);
        }
    }
}

