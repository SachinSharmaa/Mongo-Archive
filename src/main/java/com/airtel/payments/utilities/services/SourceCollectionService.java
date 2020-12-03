package com.airtel.payments.utilities.services;

import com.airtel.payments.utilities.configs.TransferMapping;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.Date;
import java.util.List;

public interface SourceCollectionService {

    public MongoCursor<Document> initiateFind(TransferMapping transferMapping, Date lastArchivedDate, int batchSize);

    public List<Document> fetchNextBatch(MongoCursor<Document> iterator, int batchSize);


}
