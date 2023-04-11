package com.bank_accounts.service;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Holder;
import com.bank_accounts.domain.enums.ErrorMessage;
import com.bank_accounts.domain.exceptions.*;
import com.bank_accounts.domain.mappers.AccountDTOMapper;
import com.bank_accounts.domain.repositories.AccountRepository;
import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.domain.repositories.HolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final HolderRepository holderRepository;
    private final AccountDTOMapper accountDTOMapper;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              HolderRepository holderRepository,
                              AccountDTOMapper accountDTOMapper) {
        this.accountRepository = accountRepository;
        this.holderRepository = holderRepository;
        this.accountDTOMapper = accountDTOMapper;
    }

    @Override
    @Transactional
    public void openAccount(AccountDTO accountDTO) {
        accountRepository.findByIban(accountDTO.iban()).ifPresent(s -> {
            throw new AccountAlreadyExistsException(String.format(
                    ErrorMessage.ERROR_001_ACCOUNT_ALREADY_EXISTS.getMessage(), accountDTO.iban()
            ));
        });

        Account account = new Account();

        account.setIban(accountDTO.iban());
        account.setBalance(accountDTO.balance());
        account.setDateOpened(LocalDateTime.now());
        account.setOverdraft(false);


        accountRepository.save(account);
    }

    @Override
    public AccountDTO getAccountByIban(String iban) {
        Account account = accountRepository.findByIban(iban).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), iban
                )));

        return accountDTOMapper.apply(account);
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
                )));

        return accountDTOMapper.apply(account);
    }

    @Override
    public List<AccountDTO> fetchAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(accountDTOMapper)
                .toList();
    }

    @Override
    public Page<AccountDTO> getAllAccounts(Pageable pageable) {

        return null;
    }

    @Override
    @Transactional
    public void depositFunds(Long id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
            new AccountDoesNotExistException(String.format(
                    ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
            )));

        account.setBalance(account.getBalance() + amount);

        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void withdrawFunds(Long id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
                )));

        if (account.getBalance() - amount < 0 && !account.getOverdraft()) {
            throw new InsufficientFundsException(ErrorMessage.ERROR_009_INSUFFICIENT_FUNDS.getMessage());
        } else {
            account.setBalance(account.getBalance() - amount);

            accountRepository.save(account);
        }
    }

    @Override
    @Transactional
    public void assignHolderToAccount(Long id, HolderDTO holderDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
                )));
        Holder holder = holderRepository.findBySsn(holderDTO.ssn()).orElseThrow(() ->
                new HolderDoesNotExistException(String.format(
                        ErrorMessage.ERROR_006_HOLDER_DOES_NOT_EXIST.getMessage(), holderDTO.ssn()
                )));

        if (account.getHolders().contains(holder)) {
            throw new HolderAlreadyInAccountException(String.format(
                    ErrorMessage.ERROR_010_HOLDER_ALREADY_IN_ACCOUNT.getMessage(),
                    holderDTO.ssn(),
                    account.getIban()
            ));
        } else {
            account.getHolders().add(holder);

            accountRepository.save(account);
        }
    }

    @Override
    @Transactional
    public void removeHolderFromAccount(Long id, HolderDTO holderDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
                )));
        Holder holder = holderRepository.findBySsn(holderDTO.ssn()).orElseThrow(() ->
                new HolderDoesNotExistException(String.format(
                        ErrorMessage.ERROR_006_HOLDER_DOES_NOT_EXIST.getMessage(), holderDTO.ssn()
                )));

        if (!account.getHolders().contains(holder)) {
            throw new HolderAlreadyInAccountException(String.format(
                    ErrorMessage.ERROR_010_HOLDER_ALREADY_IN_ACCOUNT.getMessage(),
                    holderDTO.ssn(),
                    account.getIban()
            ));
        } else {
            checkIfSingleHolder(account);
            account.getHolders().remove(holder);

            accountRepository.save(account);
        }

    }

    @Override
    @Transactional
    public void toggleOverDraft(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new AccountDoesNotExistException(String.format(
                        ErrorMessage.ERROR_003_ACCOUNT_DOES_NOT_EXIST.getMessage(), id
                )));


        account.setOverdraft(!account.getOverdraft());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {

        accountRepository.deleteById(id);
    }

    private void checkIfSingleHolder(Account account) {
        if (account.getHolders().size() == 1) {
            throw new SingleHolderAccountException(String.format(
                    ErrorMessage.ERROR_012_SINGLE_HOLDER_ACCOUNT.getMessage(), account.getIban()
            ));
        }
    }
}
