package com.adaptavist.yt.output;

import com.adaptavist.yt.WordCount.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleOutputFormatter implements OutputFormatter {
    @Override
    public String formatOutput(Map<String, Integer> wordCount, SortOrder sortOrder, SortType sortType) {

        Map<String, Integer> sortedWordCount = getSortedWordCount(wordCount, sortOrder, sortType);

        StringBuilder output = new StringBuilder();
        sortedWordCount.forEach((key, value) -> output.append(String.format("%s: %d%n", key, value)));
        return output.toString();
    }

    private Map<String, Integer> getSortedWordCount(Map<String, Integer> wordCount, SortOrder sortOrder, SortType sortType) {
        Comparator<Map.Entry<String, Integer>> comparator = switch (sortType) {
            case WORD_LENGTH -> Map.Entry.comparingByKey(Comparator.comparingInt(String::length));
            case FREQUENCY -> Map.Entry.comparingByValue();
        };

        if (sortOrder == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return wordCount.entrySet().stream()
                .sorted(comparator
                .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
