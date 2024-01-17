package com.adaptavist.yt.processing;

import java.util.HashMap;
import java.util.Map;

public class SimpleWordCounter implements WordCounter{
    @Override
    public Map<String, Integer> countWords(String input) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = input.split(" ");
        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-Z\\d]", "").toLowerCase();

            wordCount.put(cleanedWord, wordCount.getOrDefault(cleanedWord, 0) + 1);
        }

        return wordCount;
    }
}
