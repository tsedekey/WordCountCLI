package com.adaptavist.yt.output;

import com.adaptavist.yt.WordCount;

import java.util.Map;

public interface OutputFormatter {
    String formatOutput(Map<String, Integer> wordCount, WordCount.SortOrder sortOrder, WordCount.SortType sortType);
}
