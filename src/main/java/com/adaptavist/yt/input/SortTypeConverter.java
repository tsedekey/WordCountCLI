package com.adaptavist.yt.input;

import com.adaptavist.yt.WordCountCLI.*;
import picocli.CommandLine;

public class SortTypeConverter implements CommandLine.ITypeConverter<SortType> {
    @Override
    public SortType convert(String value) {
        if ("freq".equalsIgnoreCase(value)) {
            return SortType.FREQUENCY;
        } else if ("wl".equalsIgnoreCase(value)) {
            return SortType.WORD_LENGTH;
        }
        throw new CommandLine.TypeConversionException("Invalid sort type: " + value);
    }
}

