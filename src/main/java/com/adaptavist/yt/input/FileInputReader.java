package com.adaptavist.yt.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInputReader implements InputReader{

    @Override
    public String readInput(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        if (content.length() == 0) {
            throw new IllegalArgumentException("File is empty: " + filePath);
        }
        return content.toString();
    }
}
