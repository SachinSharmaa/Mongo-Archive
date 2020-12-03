package com.airtel.payments.utilities.services;

import com.airtel.payments.utilities.Entities.ArchiveResult;
import com.airtel.payments.utilities.configs.TransferInfo;

public interface ArchiveService {

    public ArchiveResult archiveData(TransferInfo transferInfo);
}
