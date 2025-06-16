# Command-Line Wordle

Welcome to Command-Line Wordle! This is a simple, text-based version of the popular word-guessing game. Challenge your brain and see if you can guess the secret five-letter word in just five tries.

---

## What It's Built With

This game is made using **Java 21**, so it runs smoothly right in your command line.

---

## Getting Started

Ready to play? Here's how to get the game up and running on your computer:

1.  **Get the Code**: Open your terminal or command prompt and type:
    ```bash
    git clone https://github.com/jainakash2108/wordle-game.git
    ```

2.  **Go to the Game Folder**:
    ```bash
    cd wordle-game
    ```

3.  **Prepare the Game**: You'll need **Java Development Kit (JDK) 21** installed. Then, run this command to get everything ready:
    ```bash
    mvn clean install
    ```

---

## How to Run the Game

Once you've followed the setup steps, you can start playing by typing:

```bash
java -jar target/wordle-game-0.0.1-SNAPSHOT.jar
```

---

## What Gameplay Looks Like

When you start the game, you'll see some instructions. Here's an example of how a game might play out:

```
Welcome to Command-Line Wordle!
Guess the 5 letter secret word in 5 tries.
After each guess, you'll get feedback:
    - GREEN: Letter is correct and in the correct position.
    - YELLOW: Letter is correct but in the wrong position.
    - NO COLOR: Letter is incorrect.
Good luck!
--------------------------------------------------

Guesses remaining: 5
Enter your 5 letter guess word: akash
AKASH

Guesses remaining: 4
Enter your 5 letter guess word: apple
APPLE

Guesses remaining: 3
Enter your 5 letter guess word: pizza
PIZZA

Guesses remaining: 2
Enter your 5 letter guess word: paper
PAPER

Guesses remaining: 1
Enter your 5 letter guess word: water
WATER

Congratulations! You've guessed the word: WATER
```

---

## How to Play

The rules are super simple:

1.  **Your Goal**: Guess the **five-letter secret word** within **five tries**. Type your guess and hit Enter.
2.  **Understand the Feedback**: After each guess, you'll get hints:
    * **GREEN**: Means the letter is correct and in the perfect spot.
    * **YELLOW**: Means the letter is in the word, but in the wrong place.
    * **NO COLOR**: Means the letter isn't in the secret word at all.
3.  **Duplicate Letters (Important!)**: If your guess has a letter repeated more times than it appears in the secret word, only the correct number of those letters will show as yellow.
    * **Example**: If the secret word is `WATER` and you guess `OTTER`:
        * `WATER` only has one 'T'. So, even though `OTTER` has two 'T's, only one of them (the one that matches the 'T' in `WATER` but is in the wrong place) will show as yellow. The other 'T' will be no color.
4.  **Win or Try Again**: Guess the word in five tries, and you win! If not, the game ends, and you can always start a new one.

Have fun playing Command-Line Wordle!
