package com.airtel.payments.utilities.factories;

import com.airtel.payments.utilities.exceptions.MongoArchiveException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientFactory {

    private MongoClientFactory() throws IllegalAccessException{
        throw new MongoArchiveException("Object creation not allowed for "+ this.getClass());
    }


    public static MongoClient getMongoClient(String connectionString) {
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().
                applyConnectionString(connString).build();
        return MongoClients.create(mongoClientSettings);
    }

}
