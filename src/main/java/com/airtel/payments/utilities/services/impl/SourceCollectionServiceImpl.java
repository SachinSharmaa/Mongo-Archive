package com.airtel.payments.utilities.services.impl;

import com.airtel.payments.utilities.configs.MongoProperties;
import com.airtel.payments.utilities.configs.TransferMapping;
import com.airtel.payments.utilities.factories.MongoClientFactory;
import com.airtel.payments.utilities.factories.MongoPropertiesFactory;
import com.airtel.payments.utilities.services.SourceCollectionService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SourceCollectionServiceImpl implements SourceCollectionService {

    private static MongoProperties mongoProperties;
    private static MongoDatabase mongoDatabase;

    private static SourceCollectionService sourceCollectionService = null;

    private SourceCollectionServiceImpl() {
        this.mongoProperties = MongoPropertiesFactory.getMongoProperties();
        MongoClient mongoClient = MongoClientFactory.getMongoClient(mongoProperties.getSourceConnectionString());
        this.mongoDatabase = mongoClient.getDatabase(mongoProperties.getSourceDatabase());
    }

    public static SourceCollectionService getSourceCollectionService() {
        if(sourceCollectionService == null) {
            sourceCollectionService = new SourceCollectionServiceImpl();
        }
        return sourceCollectionService;
    }

    @Override
    public MongoCursor<Document> initiateFind(TransferMapping transferMapping, Date lastArchivedDate, int batchSize) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(transferMapping.getSourceCollection());
        Document compareQuery = new Document("$gt",lastArchivedDate);
        Document filter = new Document(transferMapping.getTtlIndexField(),compareQuery);
        Document projection = new Document();
        transferMapping.getRemovableFields().forEach(field -> projection.append(field,0));
        return mongoCollection.find(filter).projection(projection).iterator();
    }

    @Override
    public List<Document> fetchNextBatch(MongoCursor<Document> iterator, int batchSize) {
        List<Document> documents = new ArrayList<>();
        for(int i=0;i<batchSize;i++) {
            if(iterator.hasNext()) {
                documents.add(iterator.next());
            } else {
                break;
            }
        }
        return documents;
    }
}
