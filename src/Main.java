import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    private static final int MIN_WORD_SIZE = 4;
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

        // read dictionary
        // select RANDOM word
        List<String> dictionary = readDictionary();
        String randomWord = getRandomWord(dictionary);

        Set<String> correctLetters = new LinkedHashSet<>();
        Set<String> incorrectLetters = new LinkedHashSet<>();
        int mistakeCount = 0;

        //game loop
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
        // guess letter
        // check letter presence

        while (true) {
            String letter = getInputLetter();

            // already tried (wrong)
            if (incorrectLetters.contains(letter)) {
                System.out.println("You've already tried this letter!");
                continue;
            }

            // already revealed (correct)
            if (correctLetters.contains(letter)) {
                System.out.println("You've already revealed this letter!");
                continue;
            }

            // wrong letter
            if (!word.contains(letter)) {
                System.out.println("Incorrect!");
                incorrectLetters.add(letter);
                return false;
            }

            // correct letter
            System.out.println("You are right!");
            correctLetters.add(letter);
            return true;
        }
    }

    public static List<String> readDictionary() {
        List<String> dictionary = new ArrayList<>();

        // read only words with length > 4
        // store them to array
        try {
            Scanner dictionaryScanner = new Scanner(Path.of("russian_nouns.txt"), UTF_8);

            while (dictionaryScanner.hasNext()) {
                String input = dictionaryScanner.nextLine();
                if (input.length() >= MIN_WORD_SIZE) {
                    dictionary.add(input);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read dictionary", e);
        }

        return dictionary;
    }

    public static String getRandomWord(List<String> dictionary) {
        // return RANDOM word from the dictionary

        int randomInt = RANDOM.nextInt(dictionary.size());
        return dictionary.get(randomInt);
    }

    public static String getInputLetter() {
        System.out.print("Enter the letter: ");

        // user types the letter he wants to check
        // the game accepts upper and lower case cyrillic letters
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
        // show current game state
        // show mistake count

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
        //check win
        boolean isWin = true;

        for (int i = 0; i < word.length(); i++) {
            if (!correctLetters.contains(String.valueOf(word.charAt(i)))){
                isWin = false;
            }
        }

        return isWin;
    }
}