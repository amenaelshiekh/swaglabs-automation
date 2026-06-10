package com.swaglabs.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    // static block: runs ONCE, the first time this class is used
    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on the classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        // a -D system property overrides the file (used by CI to force headless=true)
        String override = System.getProperty(key);
        if (override != null) {
            return override;
        }
        return properties.getProperty(key);
    }

    private ConfigReader() {}   // utility class — never meant to be instantiated
}