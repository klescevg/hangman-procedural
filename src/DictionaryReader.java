import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DictionaryReader {
    private static final String FILE = "russian_nouns.txt";
    private static final int MIN_WORD_SIZE = 4;

    public static List<String> read() {
        List<String> dictionary = new ArrayList<>();

        // read only words with length > 4
        // store them to array
        try {
            Scanner dictionaryScanner = new Scanner(Path.of(FILE), UTF_8);

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
}
