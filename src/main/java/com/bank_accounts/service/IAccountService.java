package com.bank_accounts.service;

import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    boolean createAccount(Account newAccount);

    Optional<Account> readAccountInfo(String iban);

    List<Account> readAllAccounts() throws NoAccountsFoundException;

    boolean changeAccountBalance(String iban, Double amount) throws EntityNotFoundException, EntryAlreadyExistsException, InsufficientFundsException;

    boolean deleteAccount(String iban) throws AccountHasBalanceException;

}
