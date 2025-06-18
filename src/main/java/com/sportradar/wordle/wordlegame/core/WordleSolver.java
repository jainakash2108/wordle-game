package com.sportradar.wordle.wordlegame.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.sportradar.wordle.wordlegame.utils.GameRules.WORD_LENGTH;

@Component
public class WordleSolver {

    public Feedback[] solveWordle(String correctWord, String guessWord) {
        if (correctWord == null || guessWord == null || correctWord.length() != WORD_LENGTH || guessWord.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Correct word length does not match guess word length!");
        }
        Feedback[] feedback = new Feedback[WORD_LENGTH];
        char[] correctWordLetters = correctWord.toCharArray();
        char[] guessWordLetters = guessWord.toCharArray();
        Map<Character, Integer> correctWordLetterFrequency = new HashMap<>();
        for (char c : correctWordLetters) {
            correctWordLetterFrequency.put(c, correctWordLetterFrequency.getOrDefault(c, 0) + 1);
        }
        for (int index = 0; index < WORD_LENGTH; index++) {
            if (guessWordLetters[index] == correctWordLetters[index]) {
                feedback[index] = Feedback.CORRECT_WORD_IN_CORRECT_POSITION;
                correctWordLetterFrequency.put(guessWordLetters[index], correctWordLetterFrequency.get(guessWordLetters[index]) - 1);
            }
        }
        for (int index = 0; index < WORD_LENGTH; index++) {
            if (feedback[index] == null) {
                if (correctWordLetterFrequency.containsKey(guessWordLetters[index]) && correctWordLetterFrequency.get(guessWordLetters[index]) > 0) {
                    feedback[index] = Feedback.CORRECT_WORD_IN_INCORRECT_POSITION;
                    correctWordLetterFrequency.put(guessWordLetters[index], correctWordLetterFrequency.get(guessWordLetters[index]) - 1);
                } else {
                    feedback[index] = Feedback.INCORRECT_WORD;
                }
            }
        }
        return feedback;
    }
}
