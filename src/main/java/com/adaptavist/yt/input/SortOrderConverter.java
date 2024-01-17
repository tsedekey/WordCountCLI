package com.adaptavist.yt.input;

import com.adaptavist.yt.WordCountCLI;
import picocli.CommandLine;

public class SortOrderConverter implements CommandLine.ITypeConverter<WordCountCLI.SortOrder>{

    @Override
    public WordCountCLI.SortOrder convert(String value) {
        if ("asc".equalsIgnoreCase(value)) {
            return WordCountCLI.SortOrder.ASC;
        } else if ("desc".equalsIgnoreCase(value)) {
            return WordCountCLI.SortOrder.DESC;
        }
        throw new CommandLine.TypeConversionException("Invalid sort order: " + value);
    }
}
