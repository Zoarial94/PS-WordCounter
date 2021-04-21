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
                        // Remove special characters. There is a specific case where we need to keep ’
                        // Words like night' or o'clock
                        line = line.replaceAll("[:;,.?!”“\"/_()\\[\\]#*$%&‘-]+’?| [’']|[’'] ", " ")
                        // Remove duplicate spaces (Ensures there are no empty words)
                        .replaceAll(" {2,}", " ")
                        .trim() // There may be a space at the beginning. This removes that and prevents an empty word.
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

        // Sort by number, then by word.
        wordSet.entrySet().stream().sorted((e1, e2)->{
            int cmp = e1.getValue().compareTo(e2.getValue());
            if(cmp == 0) {
                return e1.getKey().compareTo(e2.getKey());
            }
            return cmp;
        }).forEach(System.out::println);


    }
}
