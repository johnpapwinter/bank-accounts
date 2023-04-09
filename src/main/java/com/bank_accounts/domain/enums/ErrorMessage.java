package com.bank_accounts.domain.enums;

public enum ErrorMessage {
    ERROR_001_ACCOUNT_ALREADY_EXISTS("Account %s already exists", 1),
    ERROR_002_ACCOUNT_BALANCE_NOT_ZERO("Account balance is not zero", 0),
    ERROR_003_ACCOUNT_DOES_NOT_EXIST("Account %s does not exist", 1),
    ERROR_004_ACCOUNT_NOT_OVERDRAFT("Account %s not overdraft", 1),
    ERROR_005_HOLDER_ALREADY_EXISTS("Holder %s already exists", 1),
    ERROR_006_HOLDER_DOES_NOT_EXIST("Holder %s does not exist", 1),
    ERROR_007_NO_ACCOUNTS_EXIST("There are no accounts in the system", 0),
    ERROR_008_NO_HOLDERS_EXIST("There are no holders in the system", 0),
    ;


    final String message;
    final Integer noOfParams;

    ErrorMessage(String message, Integer noOfParams) {
        this.message = message;
        this.noOfParams = noOfParams;
    }

    public String getMessage() {
        return message;
    }

    public Integer getNoOfParams() {
        return noOfParams;
    }
}
