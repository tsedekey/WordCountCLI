package com.adaptavist.yt.input;

import com.adaptavist.yt.WordCount;
import picocli.CommandLine;

public class SortOrderConverter implements CommandLine.ITypeConverter<WordCount.SortOrder>{

    @Override
    public WordCount.SortOrder convert(String value) {
        if ("asc".equalsIgnoreCase(value)) {
            return WordCount.SortOrder.ASC;
        } else if ("desc".equalsIgnoreCase(value)) {
            return WordCount.SortOrder.DESC;
        }
        throw new CommandLine.TypeConversionException("Invalid sort order: " + value);
    }
}
