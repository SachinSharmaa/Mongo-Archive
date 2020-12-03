package com.airtel.payments.utilities.exceptions;

public class MongoArchiveException extends RuntimeException {

    public MongoArchiveException(String message) {
        System.out.println(message);
        System.exit(1);
    }
}
