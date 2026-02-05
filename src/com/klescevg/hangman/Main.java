package com.klescevg.hangman;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int MAX_MISTAKE_COUNT = 6;

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final String REGEX = "[А-ЯЁа-яё]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public static void main(String[] args){
        startGame();
    }

    public static void startGame() {
        System.out.println("Welcome to new game!");

        List<String> dictionary = DictionaryReader.read();
        String randomWord = getRandomWord(dictionary);

        Set<String> correctLetters = new LinkedHashSet<>();
        Set<String> incorrectLetters = new LinkedHashSet<>();
        int mistakeCount = 0;

        while (true){
            boolean roundResult = playRound(randomWord, correctLetters, incorrectLetters);
            if (!roundResult){
                mistakeCount++;
            }

            showResults(randomWord, correctLetters, incorrectLetters, mistakeCount);

            if (mistakeCount == MAX_MISTAKE_COUNT){
                System.out.println("You lost!");
                break;
            }

            if(checkWin(randomWord, correctLetters)){
                System.out.println("You won!");
                break;
            }
        }
    }

    public static boolean playRound(String word, Set<String> correctLetters, Set<String> incorrectLetters) {
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
                return false;
            }

            System.out.println("You are right!");
            correctLetters.add(letter);
            return true;
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
                System.out.println("Incorrect input. Try again!");
            }
        }
    }

    public static void showResults(String word, Set<String> correctLetters, Set<String> incorrectLetters, int mistakeCount) {
        System.out.print("\nWord: ");

        for (int i = 0; i < word.length(); i++) {
            String characterAtIndex = String.valueOf(word.charAt(i));
            if (correctLetters.contains(characterAtIndex)) {
                System.out.print(characterAtIndex);
            } else {
                System.out.print("_");
            }
        }

        System.out.println();
        System.out.println(STR."Mistakes (\{mistakeCount}): \{Arrays.toString(incorrectLetters.toArray())}");
    }

    public static boolean checkWin(String word, Set<String> correctLetters) {

        for (int i = 0; i < word.length(); i++) {
            if (!correctLetters.contains(String.valueOf(word.charAt(i)))){
                return false;
            }
        }

        return true;
    }
}