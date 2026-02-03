import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    private static int MIN_WORD_SIZE = 4;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
        List<String> dictionary = readDictionary();
        System.out.println("Random word: " + getRandomWord(dictionary));
        System.out.println("Hello world!");
    }

    public static void startGameRound() {
        // read dictionary

        // game loop
        // select random word
        // play round
    }

    public void playRound() {
        // round loop
        // guess letter
        // check letter presence
        // show result after each guess
    }

    public static List<String> readDictionary() throws IOException {
        List<String> dictionary = new ArrayList<>();

        // scanner only words with length>4
        // store them to array
        Scanner scanner = new Scanner(Path.of("russian_nouns.txt"), UTF_8);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.length() >= MIN_WORD_SIZE) {
                dictionary.add(input);
            }
        }

        return dictionary;
    }

    // returns random word from the dictionary
    public static String getRandomWord(List<String> dictionary) {
        int randomInt = random.nextInt(dictionary.size());
        return dictionary.get(randomInt);
    }

    public static void inputLetter() {
        // user types the letter he wants to check
    }

    public static void containsLetter() {
        // checks if the word contains provided letter
    }

    public static void showResults() {
        // show mistakes count
        // show current hangman state
        // show
    }


}