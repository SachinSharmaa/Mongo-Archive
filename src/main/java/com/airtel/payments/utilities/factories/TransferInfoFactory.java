package com.airtel.payments.utilities.factories;

import com.airtel.payments.utilities.configs.TransferInfo;
import com.airtel.payments.utilities.constants.EnvironmentVariables;
import com.airtel.payments.utilities.exceptions.MongoArchiveException;
import com.airtel.payments.utilities.utils.FileReaderUtil;

public class TransferInfoFactory {

    private TransferInfoFactory() {
        throw new MongoArchiveException("Object creation not allowed for "+ this.getClass());
    }

    public static TransferInfo getTransferInfo() {
        String filePath = System.getProperty(EnvironmentVariables.TRANSFER_INFO_ENV_VARIABLE.getValue());
        if(filePath == null) {
            throw new MongoArchiveException("Please provide mappings file path as command line argument " + EnvironmentVariables.TRANSFER_INFO_ENV_VARIABLE.getValue());
        }
        return FileReaderUtil.readJsonFile(TransferInfo.class,filePath);
    }
}
