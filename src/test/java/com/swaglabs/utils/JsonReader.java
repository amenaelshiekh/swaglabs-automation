package com.swaglabs.utils;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static <T> T[] readArray(String resourcePath, Class<T[]> type) {
        try (InputStream input = JsonReader.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (input == null) {
                throw new RuntimeException("Test data file not found: " + resourcePath);
            }
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            return new Gson().fromJson(reader, type);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON: " + resourcePath, e);
        }
    }
}