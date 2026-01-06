package com.apitesting.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getBaseUrl() {
        String env = properties.getProperty("active.env", "dev");
        return properties.getProperty("base.url." + env);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getConnectionTimeout() {
        return Integer.parseInt(properties.getProperty("connection.timeout", "10000"));
    }

    public int getResponseTimeout() {
        return Integer.parseInt(properties.getProperty("response.timeout", "30000"));
    }

    public String getApiKey() {
        return properties.getProperty("api.key");
    }
}