package com.airtel.payments.utilities.configs;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class TransferMapping {

    private String sourceCollection;
    private String destinationCollection;
    @ToString.Exclude
    private String ttlIndexField;
    @ToString.Exclude
    private List<String> removableFields;

}
