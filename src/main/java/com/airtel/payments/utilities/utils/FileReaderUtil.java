package com.airtel.payments.utilities.utils;

import com.airtel.payments.utilities.exceptions.MongoArchiveException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class FileReaderUtil {

    private FileReaderUtil() {
        throw new MongoArchiveException("Object creation not allowed for " + this.getClass());
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Properties readPropertiesFile(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (FileNotFoundException e) {
            throw new MongoArchiveException("File not found at path " + filePath);
        } catch (IOException e) {
            throw new MongoArchiveException("Unable to load properties from  " + filePath);
        }
    }

    public static <T>T readJsonFile(Class<T> clazz,  String filePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
            return objectMapper.readValue(fileBytes,clazz);
        } catch (IOException e) {
            throw new MongoArchiveException("Unable to read and parse data from " + filePath);
        }

    }
}
