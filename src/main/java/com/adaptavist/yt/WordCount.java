package com.adaptavist.yt;

import com.adaptavist.yt.input.FileInputReader;
import com.adaptavist.yt.input.InputReader;
import com.adaptavist.yt.input.SortOrderConverter;
import com.adaptavist.yt.input.SortTypeConverter;
import com.adaptavist.yt.output.OutputFormatter;
import com.adaptavist.yt.output.SimpleOutputFormatter;
import com.adaptavist.yt.processing.SimpleWordCounter;
import com.adaptavist.yt.processing.WordCounter;
import picocli.CommandLine;

import java.util.Map;

@CommandLine.Command(name = "WordCount", mixinStandardHelpOptions = true, version = "1.0",
        description = "A simple word counting application.", separator = " ")
public class WordCount  implements Runnable {

    public enum SortType {
        FREQUENCY,
        WORD_LENGTH,
    }

    public enum SortOrder {
        ASC,
        DESC,
    }
    private final InputReader inputReader;
    private final WordCounter wordCounter;
    private final OutputFormatter outputFormatter;

    public WordCount(InputReader inputReader, WordCounter wordCounter, OutputFormatter outputFormatter) {
        this.inputReader = inputReader;
        this.wordCounter = wordCounter;
        this.outputFormatter = outputFormatter;
    }

    @CommandLine.Parameters(paramLabel = "<file-path>", description = "Path to the input text file")
    private String filePath;

    @CommandLine.Option(names = {"-o", "--order"}, converter = SortOrderConverter.class,
            description = "Sort order (ascending=asc or descending=desc) default=desc")
    private SortOrder sortOrder = SortOrder.DESC;

    @CommandLine.Option(names = {"-s", "--sort"}, converter = SortTypeConverter.class,
            description = "Sort type  .(frequency=freq or word length=wl) default=freq")
    private SortType sortType = SortType.FREQUENCY;


    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new WordCount(new FileInputReader(), new SimpleWordCounter(), new SimpleOutputFormatter()));
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            String fileContent = inputReader.readInput(filePath);
            Map<String, Integer> wordsCount = wordCounter.countWords(fileContent);
            String formattedOutput = outputFormatter.formatOutput(wordsCount, sortOrder, sortType);
            System.out.println(formattedOutput);
        } catch (Exception e) {
            System.err.println("Error processing the file: " + e.getMessage());
        }
    }
}
