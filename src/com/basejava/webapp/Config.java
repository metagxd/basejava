package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(".\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    Properties properties = new Properties();
    private File storageDir;


    private Config() {
        try (InputStream inputStream = new FileInputStream(PROPS)) {
            properties.load(inputStream);
            storageDir = new File(properties.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file!" + PROPS.getAbsolutePath(), e);
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
