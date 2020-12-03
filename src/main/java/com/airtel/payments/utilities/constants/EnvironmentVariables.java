package com.airtel.payments.utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnvironmentVariables {

    MONGO_INPUT_ENV_VARIABLE("mongoPropertyFilePath"),
    TRANSFER_INFO_ENV_VARIABLE("transferInfoFilePath");

    private String value;
}
