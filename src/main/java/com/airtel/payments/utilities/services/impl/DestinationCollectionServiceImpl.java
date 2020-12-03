package com.airtel.payments.utilities.services.impl;

import com.airtel.payments.utilities.configs.MongoProperties;
import com.airtel.payments.utilities.configs.TransferMapping;
import com.airtel.payments.utilities.factories.MongoClientFactory;
import com.airtel.payments.utilities.factories.MongoPropertiesFactory;
import com.airtel.payments.utilities.services.DestinationCollectionService;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;

import java.util.Date;
import java.util.List;

public class DestinationCollectionServiceImpl implements DestinationCollectionService {

    private static MongoProperties mongoProperties;
    private static MongoDatabase mongoDatabase;

    private static DestinationCollectionService destinationCollectionService = null;

    private DestinationCollectionServiceImpl() {
        this.mongoProperties = MongoPropertiesFactory.getMongoProperties();
        MongoClient mongoClient = MongoClientFactory.getMongoClient(mongoProperties.getDestinationConnectionString());
        this.mongoDatabase = mongoClient.getDatabase(mongoProperties.getDestinationDatabase());
    }

    public static DestinationCollectionService getDestinationCollectionService() {
        if(destinationCollectionService == null) {
            destinationCollectionService = new DestinationCollectionServiceImpl();
        }
        return destinationCollectionService;
    }
    @Override
    public Date getLatestTimeStamp(TransferMapping transferMapping) {
       try {
           MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(transferMapping.getDestinationCollection());
           Document sort = new Document(transferMapping.getTtlIndexField(),-1);
           Document document = mongoCollection.find().sort(sort).first();
           return document.getDate(transferMapping.getTtlIndexField());
       } catch (Exception exception) {
           return new Date(Long.MIN_VALUE);
       }
    }

    @Override
    public int saveDocuments(TransferMapping transferMapping, List<Document> documents) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(transferMapping.getDestinationCollection());
        try {
            InsertManyResult insertManyResult = mongoCollection.insertMany(documents);
            return insertManyResult.getInsertedIds().values().size();
        } catch (MongoBulkWriteException writeException) {
            return writeException.getWriteErrors().get(0).getIndex();
        }
    }
}
