package com.adaptavist.yt.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInputReader implements InputReader{

    private static final Logger logger = LoggerFactory.getLogger(FileInputReader.class);

    @Override
    public String readInput(String filePath) {
        StringBuilder content = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            logger.error("Error reading file: " + filePath, e);
        }
        return content.toString();
    }
}
