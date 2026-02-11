package com.klescevg.hangman;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HangmanGame {
    private static final int MAX_MISTAKE_COUNT = 6;

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final String REGEX = "[А-ЯЁа-яё]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static String word;
    private static final Set<String> correctLetters = new LinkedHashSet<>();
    private static final Set<String> incorrectLetters = new LinkedHashSet<>();

    public static void playGame(List<String> words) {
        System.out.println("Welcome to new game!");

        word = getRandomWord(words);

        while (!isGameOver()) {
            showRoundResults();
            playRound();
        }

        showGameResults();
    }

    public static void playRound() {
        while (true) {
            String letter = getInputLetter();

            if (incorrectLetters.contains(letter)) {
                System.out.println("You've already tried this letter!");
                continue;
            }

            if (correctLetters.contains(letter)) {
                System.out.println("You've already revealed this letter!");
                continue;
            }

            if (!word.contains(letter)) {
                System.out.println("Incorrect!");
                incorrectLetters.add(letter);
                break;
            }

            System.out.println("You are right!");
            correctLetters.add(letter);
            break;
        }
    }

    public static String getRandomWord(List<String> dictionary) {
        int randomInt = RANDOM.nextInt(dictionary.size());

        return dictionary.get(randomInt);
    }

    public static String getInputLetter() {
        System.out.print("Enter the letter: ");

        while (true) {
            String input = SCANNER.nextLine();

            Matcher matcher = PATTERN.matcher(input);
            if (matcher.matches()) {
                return input.toLowerCase();
            } else {
                System.out.print("Incorrect input! Try again: ");
            }
        }
    }

    public static void showRoundResults() {
        System.out.print("\nWord: ");

        for (int i = 0; i < word.length(); i++) {
            String characterAtIndex = String.valueOf(word.charAt(i));

            if (correctLetters.contains(characterAtIndex)) {
                System.out.print(characterAtIndex);
            } else {
                System.out.print("_");
            }
        }

        System.out.println("\nMistakes (" + incorrectLetters.size() + "): " + Arrays.toString(incorrectLetters.toArray()));
    }

    public static boolean isGameOver() {
        return isWin() || isLose();
    }

    public static boolean isWin() {
        for (int i = 0; i < word.length(); i++) {
            if (!correctLetters.contains(String.valueOf(word.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isLose() {
        return incorrectLetters.size() == MAX_MISTAKE_COUNT;
    }

    public static void showGameResults() {
        System.out.println();
        if (isWin()) {
            System.out.println("You won! The word is " + word);
        } else if (isLose()) {
            System.out.println("You lost... The word is " + word);
        } else {
            System.out.println("Game is not finished");
        }
    }
}
