package com.airtel.payments.utilities.Entities;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ArchiveResult {
    private List<String> successCollectionsList;
    private List<String> failedCollectionsList;

    public ArchiveResult() {
        this.successCollectionsList = new ArrayList<>();
        this.failedCollectionsList = new ArrayList<>();
    }
}
