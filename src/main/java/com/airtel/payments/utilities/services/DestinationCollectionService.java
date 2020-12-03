package com.airtel.payments.utilities.services;

import com.airtel.payments.utilities.configs.TransferMapping;
import org.bson.Document;

import java.util.Date;
import java.util.List;

public interface DestinationCollectionService {

    public Date getLatestTimeStamp(TransferMapping transferMapping);

    public int saveDocuments(TransferMapping transferMapping, List<Document> documents);

}
