package com.bank_accounts.service;

import com.bank_accounts.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    boolean createAccount(Account newAccount);

    Optional<Account> readAccountInfo(String iban);

    List<Account> readAllAccounts();

    boolean changeAccountBalance(String iban, Double amount);

    boolean deleteAccount(String iban);

}
