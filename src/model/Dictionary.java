package model;

import java.io.*;
import java.util.*;

public class Dictionary {
    private Set<String> words;

    public Dictionary(String filename) {
        words = new HashSet<>();
        loadWords(filename);
    }

    private void loadWords(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
            System.out.println("Dictionary loaded with " + words.size() + " words.");
        } catch (IOException e) {
            System.err.println("Error loading dictionary file: " + filename);
            e.printStackTrace();
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}
