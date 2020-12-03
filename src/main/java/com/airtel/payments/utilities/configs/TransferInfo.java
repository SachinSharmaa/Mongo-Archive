package com.airtel.payments.utilities.configs;

import lombok.Getter;

import java.util.List;

@Getter
public class TransferInfo {

    private int batchSize;
    private int retries;
    private List<TransferMapping> transferMappings;

}
