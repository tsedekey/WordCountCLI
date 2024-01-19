package com.adaptavist.yt;

import com.adaptavist.yt.input.FileInputReader;
import com.adaptavist.yt.output.SimpleOutputFormatter;
import com.adaptavist.yt.processing.SimpleWordCounter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCountTest {
    final PrintStream originalOut = System.out;
    final PrintStream originalErr = System.err;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        out.reset();
        err.reset();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @ParameterizedTest
    @DisplayName("should return word count with different files")
    @ValueSource(strings = {"src/test/resources/test-data.txt", "src/test/resources/test-data-ignore-case.txt", "src/test/resources/test-data-ignore-non-ascii.txt", "src/test/resources/test-data-ignore-punctuation.txt"})
    void shouldReturnWordCount(String filePath) {
        String[] args = {filePath};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals(
                """
                four: 4
                quarter: 4
                three: 3
                two: 2
                one: 1
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("should return word count with sort order as desc")
    void shouldReturnWordCountWithSortOrderAsAsc() {
        String[] args = {"-o asc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals(
                """
                one: 1
                two: 2
                three: 3
                four: 4
                quarter: 4
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("should return word count with sort type as word length")
    void shouldReturnWordCountWithSortTypeAsWordLength() {
        String[] args = {"-s wl", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals(
                """
                quarter: 4
                three: 3
                four: 4
                one: 1
                two: 2
                                        
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("should return word count with sort type as word length and sort order as asc")
    void shouldReturnWordCountWithSortTypeAsWordLengthAndSortOrderAsAsc() {
        String[] args = {"-s wl", "-o asc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals(
                """
                one: 1
                two: 2
                four: 4
                three: 3
                quarter: 4
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("should return word count with sort type as word length and sort order as desc")
    void shouldReturnWordCountWithSortTypeAsWordLengthAndSortOrderAsDesc() {
        String[] args = {"-s wl", "-o desc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals(
                """
                quarter: 4
                three: 3
                four: 4
                one: 1
                two: 2
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("should return error with invalid file path")
    void shouldReturnErrorWithInvalidFilePath() {
        String[] args = {"src/test/resources/invalid-file.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("Error processing the file: File not found: src/test/resources/invalid-file.txt", err.toString().trim());
        assertEquals("", out.toString());
    }

    @Test
    @DisplayName("should return error with empty file")
    void shouldReturnErrorWithEmptyFile() {
        String[] args = {"src/test/resources/empty-file.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("Error processing the file: File is empty: src/test/resources/empty-file.txt\n", err.toString());
        assertEquals("", out.toString());
    }

    @Test
    @DisplayName("should return help with no command args")
    void shouldReturnHelpWithNoCommandArgs() {
        String[] args = {};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                Missing required parameter: '<file-path>'
                Usage: WordCountCLI [-hV] [-o <sortOrder>] [-s <sortType>] <file-path>
                A simple word counting application.
                      <file-path>           Path to the input text file
                  -h, --help                Show this help message and exit.
                  -o, --order <sortOrder>   Sort order (ascending=asc or descending=desc)
                                              default=desc
                  -s, --sort <sortType>     Sort type  .(frequency=freq or word length=wl)
                                              default=freq
                  -V, --version             Print version information and exit.""", err.toString().trim());
        assertEquals("", out.toString());
    }

    @Test
    @DisplayName("should return help with invalid sort type")
    void shouldReturnHelpWithInvalidSortOrder() {
        String[] args = {"-o invalid", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                Invalid value for option '--order': Invalid sort order: invalid
                Usage: WordCountCLI [-hV] [-o <sortOrder>] [-s <sortType>] <file-path>
                A simple word counting application.
                      <file-path>           Path to the input text file
                  -h, --help                Show this help message and exit.
                  -o, --order <sortOrder>   Sort order (ascending=asc or descending=desc)
                                              default=desc
                  -s, --sort <sortType>     Sort type  .(frequency=freq or word length=wl)
                                              default=freq
                  -V, --version             Print version information and exit.""", err.toString().trim());
        assertEquals("", out.toString());
    }

    @Test
    @DisplayName("should return word count with single word file")
    void shouldReturnWordCountWithSingleWordFile() {
        String[] args = {"src/test/resources/single-word-file.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                lalalala: 1
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("Should return same word count when two files with same words but different order")
    void shouldReturnSameWordCountWhenTwoFilesSameWordsButDifferentOrder() {
        String[] args = {"src/test/resources/test-data2.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        String output1 = out.toString();
        System.out.println(output1);
        out.reset();
        args = new String[]{"src/test/resources/test-data3.txt"};
        new CommandLine(new WordCountCLI(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        String output2 = out.toString();
        System.out.println(output2);
        assertEquals(output1, output2);
    }
}