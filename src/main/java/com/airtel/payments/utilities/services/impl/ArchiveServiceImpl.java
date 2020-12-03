package com.airtel.payments.utilities.services.impl;

import com.airtel.payments.utilities.Entities.ArchiveResult;
import com.airtel.payments.utilities.configs.TransferInfo;
import com.airtel.payments.utilities.configs.TransferMapping;
import com.airtel.payments.utilities.services.ArchiveService;
import com.airtel.payments.utilities.services.DestinationCollectionService;
import com.airtel.payments.utilities.services.SourceCollectionService;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.Date;
import java.util.List;

public class ArchiveServiceImpl implements ArchiveService {

    private static SourceCollectionService sourceCollectionService;
    private static DestinationCollectionService destinationCollectionService;

    private static ArchiveService archiveServiceImpl = null;

    private ArchiveServiceImpl() {
        this.sourceCollectionService = SourceCollectionServiceImpl.getSourceCollectionService();
        this.destinationCollectionService = DestinationCollectionServiceImpl.getDestinationCollectionService();
    }

    public static ArchiveService getArchiveService() {
        if(archiveServiceImpl == null) {
            archiveServiceImpl = new ArchiveServiceImpl();
        }
        return archiveServiceImpl;
    }

    @Override
    public ArchiveResult archiveData(TransferInfo transferInfo) {
        ArchiveResult archiveResult = new ArchiveResult();
        for(TransferMapping transferMapping: transferInfo.getTransferMappings()) {
            int batchSize = transferInfo.getBatchSize();
            int retries = transferInfo.getRetries();
            boolean isSuccess = true;
            Date latestTimeStamp = destinationCollectionService.getLatestTimeStamp(transferMapping);
            MongoCursor<Document> cursor = sourceCollectionService.initiateFind(transferMapping,latestTimeStamp,batchSize);
            while(cursor.hasNext()) {
                List<Document> currentBatch = sourceCollectionService.fetchNextBatch(cursor,batchSize);
                boolean isSaveSuccessful = saveDocumentsToArchive(currentBatch, transferMapping, retries);
                if(!isSaveSuccessful) {
                    isSuccess = false;
                    break;
                }
            }
            if(isSuccess) {
                archiveResult.getSuccessCollectionsList().add(transferMapping.getSourceCollection());
            }
            else {
                archiveResult.getFailedCollectionsList().add(transferMapping.getSourceCollection());
            }
        }
        return archiveResult;
    }

    private boolean saveDocumentsToArchive(List<Document> documents, TransferMapping transferMapping, int retries){
        do {
            int noOfsavedDocuments = destinationCollectionService.saveDocuments(transferMapping,documents);
            if(noOfsavedDocuments == documents.size()) {
                return true;
            }
            --retries;
            documents = documents.subList(noOfsavedDocuments,documents.size());
        } while (retries>=0);
        return false;
    }
}
