package com.adaptavist.yt.output;

import com.adaptavist.yt.WordCountCLI;

import java.util.Map;

public interface OutputFormatter {
    String formatOutput(Map<String, Integer> wordCount, WordCountCLI.SortOrder sortOrder, WordCountCLI.SortType sortType);
}
