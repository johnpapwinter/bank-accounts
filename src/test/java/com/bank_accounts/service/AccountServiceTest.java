package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.dao.HolderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    HolderRepository holderRepository;

    @InjectMocks
    AccountService accountServiceUnderTest;

    @Test
    void createAccount() {
    }

    @Test
    void readAccountInfo() {
    }

    @Test
    void readAllAccounts() {
    }

    @Test
    void changeAccountBalance() {
    }

    @Test
    void addAccountHolder() {
    }

    @Test
    void removeAccountHolder() {
    }

    @Test
    void deleteAccount() {
    }
}