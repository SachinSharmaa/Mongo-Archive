package com.airtel.payments.utilities.configs;

import lombok.Getter;

@Getter
public class MongoProperties {

    private static MongoProperties mongoProperties = null ;

    private String sourceConnectionString;
    private String destinationConnectionString;
    private String sourceDatabase;
    private String destinationDatabase;

    private MongoProperties(String sourceConnectionString, String destinationConnectionString, String sourceDatabase, String destinationDatabase) {
        this.sourceConnectionString = sourceConnectionString;
        this.destinationConnectionString = destinationConnectionString;
        this.sourceDatabase = sourceDatabase;
        this.destinationDatabase = destinationDatabase;
    }

    public static MongoProperties getMongoProperties(String sourceConnectionString, String destinationConnectionString, String sourceDatabase, String destinationDatabase) {
        if(mongoProperties == null) {
            mongoProperties =  new MongoProperties(sourceConnectionString,destinationConnectionString,sourceDatabase,destinationDatabase);
        }
        return mongoProperties;
    }
}
