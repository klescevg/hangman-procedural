package com.klescevg.hangman;

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
        Path dictionaryPath = Path.of(FILE);

        try {
            Scanner dictionaryScanner = new Scanner(dictionaryPath, UTF_8);

            while (dictionaryScanner.hasNext()) {
                String input = dictionaryScanner.nextLine();
                if (input.length() >= MIN_WORD_SIZE) {
                    dictionary.add(input);
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot read dictionary file: ");
            System.err.println(dictionaryPath.toAbsolutePath());
            System.exit(1);
        }

        return dictionary;
    }
}
