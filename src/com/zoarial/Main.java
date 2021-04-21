package com.zoarial;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    /*
     * read the book and count how many time each word appeared
     * 1. choose a byte or char base (reader/ input) to read the book
     * 2. read into a collection and sort
     * 3. clean the data ... e.g. (- , extra spaces , upper / lower )
     * 4. display the data on the console
     */
    public static void main(String[] args) {
        HashMap<String, Integer> wordSet = new HashMap<>();
        Path file = Paths.get("resources/A Tale of Two Cities.txt");
        Stream<String> lineStream;
        try {
             lineStream = Files.lines(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        lineStream.map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .forEach((line) -> {
                        // Remove special characters
                        line = line.replaceAll("[:;,.?!”“\"_()\\[\\]#*‘-]+", " ")
                        // Remove specific case. Keeps words like night's or o'clock
                        .replaceAll(" ‘|‘ |^ +", "")
                        // Remove duplicate spaces (Ensures there are no empty words)
                        .replaceAll(" {2,}", " ")
                        .toLowerCase();

                        // If the line is now empty, don't do anything
                        if(line.isEmpty()) {
                            return;
                        }

                        // Add words to set
                        String[] words = line.split(" ");
                        for(String word : words) {
                            // Sets to 1 if null, otherwise it sums 1 and existing value.
                            wordSet.merge(word, 1, Integer::sum);
                        }
                    });

        wordSet.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).forEach(System.out::println);


    }
}
