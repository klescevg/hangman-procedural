package com.klescevg.hangman;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> dictionary = DictionaryReader.read();
        HangmanGame.playGame(dictionary);
    }
}