package com.adaptavist.yt;

import com.adaptavist.yt.input.FileInputReader;
import com.adaptavist.yt.output.SimpleOutputFormatter;
import com.adaptavist.yt.processing.SimpleWordCounter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    void withDefaultOptions() {
        String[] args = {"src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
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
    void withSortOrderAsc() {
        String[] args = {"-o asc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
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
    void withSortTypeWordLength() {
        String[] args = {"-s wl", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
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
    void withSortTypeWordLengthAndSortOrderAsc() {
        String[] args = {"-s wl", "-o asc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
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
    void withSortTypeWordLengthAndSortOrderDesc() {
        String[] args = {"-s wl", "-o desc", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
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
    void withDefaultOptionsAndInvalidFilePath() {
        String[] args = {"src/test/resources/invalid-file.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("Error processing the file: File not found: src/test/resources/invalid-file.txt", err.toString().trim());
        assertEquals("", out.toString());
    }

    @Test
    void withDefaultOptionsAndEmptyFile() {
        String[] args = {"src/test/resources/empty-file.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("Error processing the file: File is empty: src/test/resources/empty-file.txt\n", err.toString());
        assertEquals("", out.toString());
    }

    @Test
    void withNoCommandArgs() {
        String[] args = {};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                Missing required parameter: '<file-path>'
                Usage: WordCount [-hV] [-o <sortOrder>] [-s <sortType>] <file-path>
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
    void withInvalidSortOrder() {
        String[] args = {"-o invalid", "src/test/resources/test-data.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                Invalid value for option '--order': Invalid sort order: invalid
                Usage: WordCount [-hV] [-o <sortOrder>] [-s <sortType>] <file-path>
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
    void withSingleWordFile() {
        String[] args = {"src/test/resources/single-word-file.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        assertEquals("""
                lalalala: 1
                
                """, out.toString());
        assertEquals("", err.toString());
    }

    @Test
    @DisplayName("With two files with same words but different order should return same result")
    void withTwoFilesSameWordsButDifferentOrder() {
        String[] args = {"src/test/resources/test-data2.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        String output1 = out.toString();
        System.out.println(output1);
        out.reset();
        args = new String[]{"src/test/resources/test-data3.txt"};
        new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter())).execute(args);
        String output2 = out.toString();
        System.out.println(output2);
        assertEquals(output1, output2);
    }

}