package org.example.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataProcessor {
    public void saveToJson(List<String> data, String filePath){
        try (FileWriter writier = new FileWriter(filePath)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writier);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Method to parse and clean scraped data
    public List<String> cleanData(List<String> rawData){
        return rawData.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}
