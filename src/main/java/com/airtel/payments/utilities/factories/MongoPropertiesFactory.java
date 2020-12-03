package com.airtel.payments.utilities.factories;

import com.airtel.payments.utilities.constants.EnvironmentVariables;
import com.airtel.payments.utilities.configs.MongoProperties;
import com.airtel.payments.utilities.exceptions.MongoArchiveException;
import com.airtel.payments.utilities.utils.FileReaderUtil;

import java.util.Properties;

public class MongoPropertiesFactory {

    private MongoPropertiesFactory() {
        throw new MongoArchiveException("Object creation not allowed for "+ this.getClass());
    }

    public static MongoProperties getMongoProperties() {
        String filePath = System.getProperty(EnvironmentVariables.MONGO_INPUT_ENV_VARIABLE.getValue());
        if(filePath == null) {
            throw new MongoArchiveException("Please provide properties file path as command line argument " + EnvironmentVariables.MONGO_INPUT_ENV_VARIABLE.getValue());
        }
        Properties properties = FileReaderUtil.readPropertiesFile(filePath);
        return MongoProperties.
                getMongoProperties(properties.getProperty("mongo.source.connection-string"),properties.getProperty("mongo.destination.connection-string"),properties.getProperty("mongo.source.database"),properties.getProperty("mongo.destination.database"));
    }
}
