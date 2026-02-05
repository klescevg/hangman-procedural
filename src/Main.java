import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    private static int MIN_WORD_SIZE = 4;
    private static int MAX_MISTAKE_COUNT = 6;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) throws IOException {
        startGameLoop();
    }

    public static void startGameLoop() throws IOException {
        System.out.println("Welcome to new game!");

        // read dictionary
        // select random word
        List<String> dictionary = readDictionary();
        String randomWord = getRandomWord(dictionary);

        Set<String> correctLetters = new LinkedHashSet<>();
        Set<String> incorrectLetters = new LinkedHashSet<>();
        int mistakeCount = 0;

        randomWord = "саша";

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

    public static boolean playRound(String randomWord, Set<String> correctLetters, Set<String> incorrectLetters) {
        // guess letter
        // check letter presence

        while (true) {
            String letter = inputLetter();

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
            if (!randomWord.contains(letter)) {
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

    public static List<String> readDictionary() throws IOException {
        List<String> dictionary = new ArrayList<>();

        // read only words with length > 4
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

    public static String getRandomWord(List<String> dictionary) {
        // return random word from the dictionary

        int randomInt = random.nextInt(dictionary.size());
        return dictionary.get(randomInt);
    }

    public static String inputLetter() {
        System.out.print("Enter the letter: ");

        // user types the letter he wants to check
        while (true) {
            String input = scanner.nextLine();

            if (input.matches("[А-Яа-я]")) {
                return input.toLowerCase();
            } else {
                System.out.println("Incorrect input. Try again!");
            }
        }
    }

    public static void showResults(String randomWord, Set<String> correctLetters, Set<String> incorrectLetters, int mistakeCount) {
        // show current game state
        // show mistake count

        System.out.print("\nWord: ");
        for (int i = 0; i < randomWord.length(); i++) {
            String characterAtIndex = String.valueOf(randomWord.charAt(i));
            if (correctLetters.contains(characterAtIndex)) {
                System.out.print(characterAtIndex);
            } else {
                System.out.print("_");
            }
        }
        System.out.println();
        System.out.println("Mistakes (" + mistakeCount + "): " + Arrays.toString(incorrectLetters.toArray()));
    }

    public static boolean checkWin(String randomWord, Set<String> correctLetters) {
        //check win
        boolean isWin = true;

        for (int i = 0; i < randomWord.length(); i++) {
            if (!correctLetters.contains(String.valueOf(randomWord.charAt(i)))){
                isWin = false;
            }
        }

        return isWin;
    }
}