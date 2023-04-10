package com.bank_accounts.service;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.enums.ErrorMessage;
import com.bank_accounts.domain.exceptions.*;
import com.bank_accounts.domain.mappers.AccountDTOMapper;
import com.bank_accounts.domain.mappers.HolderDTOMapper;
import com.bank_accounts.domain.repositories.AccountRepository;
import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.domain.repositories.HolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final HolderServiceImpl holderService;
    private final AccountDTOMapper accountDTOMapper;
    private final HolderDTOMapper holderDTOMapper;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              HolderServiceImpl holderService,
                              AccountDTOMapper accountDTOMapper,
                              HolderDTOMapper holderDTOMapper) {
        this.accountRepository = accountRepository;
        this.holderService = holderService;
        this.accountDTOMapper = accountDTOMapper;
        this.holderDTOMapper = holderDTOMapper;
    }

    @Override
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
        return null;
    }

    @Override
    public List<AccountDTO> fetchAllAccounts() {
        return null;
    }

    @Override
    public Page<AccountDTO> getAllAccounts(Pageable pageable) {
        return null;
    }

    @Override
    public void depositFunds(Long id, Double amount) {

    }

    @Override
    public void withdrawFunds(Long id, Double amount) {

    }

    @Override
    public void assignHolderToAccount(Long id, HolderDTO holderDTO) {

    }

    @Override
    public void removeHolderFromAccount(Long id, HolderDTO holderDTO) {

    }

    @Override
    public void deleteAccount(Long id) {

    }
}
