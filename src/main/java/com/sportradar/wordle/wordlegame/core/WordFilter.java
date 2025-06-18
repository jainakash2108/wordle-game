package com.sportradar.wordle.wordlegame.core;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_LENGTH;
import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_PATTERN;
import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_SPLIT_PATTERN;
import static java.util.stream.Collectors.toSet;

@Component
public class WordFilter {

    public Set<String> filterValidWords(Set<String> rawWords) {
        return rawWords.stream()
                .flatMap(Pattern.compile(WORD_SPLIT_PATTERN)::splitAsStream)
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(word -> word.length() == WORD_LENGTH)
                .filter(word -> word.matches(WORD_PATTERN))
                .collect(toSet());
    }
}