package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final HolderService holderService;


    public AccountService(AccountRepository accountRepository, HolderService holderService) {
        this.accountRepository = accountRepository;
        this.holderService = holderService;
    }


    @Override
    public int createAccount(Account newAccount, String ssn) throws EntryAlreadyExistsException, EntityNotFoundException {
        if (readAccountInfo(newAccount.getIban()).isPresent()) {
            throw new EntryAlreadyExistsException(newAccount.getIban());
        }
        Account account = new Account(newAccount.getIban(), newAccount.getBalance(), newAccount.getOverdraft());

        Optional<Holder> holder = holderService.readHolder(ssn);
        if (holder.isPresent()) {
            account.addHolder(holder.get());
        } else {
            throw new EntityNotFoundException(ssn);
        }
        accountRepository.save(account);
        return 0;
    }

    @Override
    public Optional<Account> readAccountInfo(String iban) {
        return accountRepository.findByIban(iban);

    }

    @Override
    public List<Account> readAllAccounts() throws NoAccountsFoundException {
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) {
            throw new NoAccountsFoundException();
        }
        return accounts;
    }

    @Override
    public int changeAccountBalance(String iban, Double amount) throws InsufficientFundsException {
        Optional<Account> account = readAccountInfo(iban);
        double currentBalance = account.get().getBalance();
        double newBalance = currentBalance + amount;
        if (newBalance < 0) {
            if(checkIfOverdraft(account.get())) {
                account.get().setBalance(newBalance);
                System.out.println("The account was credited");
            } else {
                throw new InsufficientFundsException(iban);
            }
        }
        account.get().setBalance(newBalance);
        accountRepository.save(account.get());

        return 0;
    }

    // TODO Cleanup
    @Override
    public int addAccountHolder(String iban, String ssn) throws EntityNotFoundException {
        Optional<Account> account = readAccountInfo(iban);
        Optional<Holder> holder = holderService.readHolder(ssn);
        holderService.addAccount(account.get(), ssn);
        account.get().addHolder(holder.get());
        accountRepository.save(account.get());
        return 0;
    }


    @Override
    public int removeAccountHolder(String iban, Holder holder) throws AccountMustHaveOneHolderException {
        Optional<Account> account = readAccountInfo(iban);
        if (account.get().getHolders().isEmpty()) {
            throw new AccountMustHaveOneHolderException(iban);
        }
        account.get().removeHolder(holder);
        holder.removeAccount(account.get());
        accountRepository.save(account.get());
        return 0;
    }

    @Override
    public int deleteAccount(String iban) throws AccountHasBalanceException {
        Optional<Account> account = accountRepository.findByIban(iban);
        if (account.get().getBalance() > 0) {
            throw new AccountHasBalanceException(iban, account.get().getBalance());
         }
        accountRepository.deleteById(account.get().getId());
        return 0;
    }

    private boolean checkIfOverdraft(Account account) {
        return account.getOverdraft();
    }
}
