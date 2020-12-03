package com.airtel.payments.utilities;

import com.airtel.payments.utilities.Entities.ArchiveResult;
import com.airtel.payments.utilities.services.ArchiveService;
import com.airtel.payments.utilities.services.impl.ArchiveServiceImpl;
import com.airtel.payments.utilities.exceptions.MongoArchiveException;
import com.airtel.payments.utilities.factories.TransferInfoFactory;

public class MongoArchiveApplication {

    public static void main(String ...args) {
        ArchiveService archiveService = ArchiveServiceImpl.getArchiveService();
        ArchiveResult archiveResult = archiveService.archiveData(TransferInfoFactory.getTransferInfo());
        if(!archiveResult.getFailedCollectionsList().isEmpty()) {
            throw new MongoArchiveException(archiveResult.toString());
        }
        else {
            System.out.println(archiveResult.toString());
        }
    }

}
