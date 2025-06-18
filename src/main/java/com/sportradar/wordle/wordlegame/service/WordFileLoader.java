package com.sportradar.wordle.wordlegame.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WordFileLoader {

    @Value("${game.word.file}")
    private String fileName;

    public Set<String> loadWords() {
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
            return lines.collect(Collectors.toSet());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read word file: " + path, e);
        }
    }
}
