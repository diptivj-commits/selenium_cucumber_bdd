package com.automation.config;
import java.io.*;
import java.util.Properties;
public final class ConfigReader {
    private static final Properties VALUES = load();
    private ConfigReader() {}
    private static Properties load() {
        Properties values = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new IllegalStateException("config.properties is missing from src/test/resources");
            values.load(input); return values;
        } catch (IOException e) { throw new IllegalStateException("Cannot read configuration", e); }
    }
    public static String get(String key) {
        String value = System.getProperty(key, VALUES.getProperty(key));
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Missing configuration: " + key);
        return value.trim();
    }
    public static int positiveInt(String key) {
        int value = Integer.parseInt(get(key));
        if (value <= 0) throw new IllegalArgumentException(key + " must be positive");
        return value;
    }
}
