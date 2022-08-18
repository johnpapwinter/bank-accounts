package com.bank_accounts.service;

import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    int createAccount(Account newAccount, String ssn) throws EntryAlreadyExistsException, EntityNotFoundException;

    Optional<Account> readAccountInfo(String iban) throws EntityNotFoundException;

    List<Account> readAllAccounts() throws NoAccountsFoundException;

    int changeAccountBalance(String iban, Double amount) throws EntityNotFoundException, EntryAlreadyExistsException, InsufficientFundsException;

    int addAccountHolder(String iban, String ssn) throws EntityNotFoundException;

    int removeAccountHolder (String iban, Holder holder) throws EntityNotFoundException, AccountMustHaveOneHolderException;

    int deleteAccount(String iban) throws AccountHasBalanceException;

}
