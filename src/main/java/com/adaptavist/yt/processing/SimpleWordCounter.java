package com.adaptavist.yt.processing;

import java.util.HashMap;
import java.util.Map;

public class SimpleWordCounter implements WordCounter{
    @Override
    public Map<String, Integer> countWords(String input) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = input.split(" ");
        for (String word : words) {
            String cleanedWord = removerPunctuation(word).toLowerCase();
            if (isAscii(word)) {
                wordCount.put(cleanedWord, wordCount.getOrDefault(cleanedWord, 0) + 1);
            }
        }
        return wordCount;
    }

    private String removerPunctuation(String word) {
        return word.replaceAll("[^a-zA-Z\\d]", "");
    }

    private static boolean isAscii(String word) {
        return word.matches("\\A\\p{ASCII}*\\z");
    }
}
