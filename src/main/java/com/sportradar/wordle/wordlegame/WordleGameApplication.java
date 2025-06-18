package com.sportradar.wordle.wordlegame;

import com.sportradar.wordle.wordlegame.service.WordleGameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordleGameApplication implements CommandLineRunner {

    private final WordleGameService gameService;

    public WordleGameApplication(WordleGameService gameService) {
        this.gameService = gameService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WordleGameApplication.class, args);
    }

    @Override
    public void run(String... args) {
        gameService.startGame();
    }
}