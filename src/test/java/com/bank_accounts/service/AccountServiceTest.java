package com.bank_accounts.service;

import com.bank_accounts.repositories.AccountRepository;
import com.bank_accounts.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    HolderServiceImpl holderService;

    @InjectMocks
    AccountServiceImpl accountServiceUnderTest;

    @Test
    void shouldCreateAccount() {
        //given
        Account account = new Account("10101", 0.0, false);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.empty());

        //when
        boolean checkIfTrue = accountServiceUnderTest.createAccount(account);

        //then
        verify(accountRepository, times(1)).save(account);
        assertTrue(checkIfTrue);
    }

    @Test
    void shouldThrowExceptionIfAccountExistsInCreateAccount() {
        //given
        Account account = new Account("10101", 0.0, false);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        //then
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.createAccount(account));
    }

    @Test
    void shouldReadAccountInfo() {
        //given
        Account account = new Account("10101", 0.0, false);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        Optional<Account> foundAccount = accountServiceUnderTest.readAccountInfo(account.getIban());

        //then
        assertEquals(account, foundAccount.get());
    }

    @Test
    void shouldReadAllAccounts() {
        //given
        Account account1 = new Account("10101", 0.0, false);
        Account account2 = new Account("20202", 10.0, true);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        when(accountRepository.findAll()).thenReturn(accountList);

        //when
        List<Account> foundList = accountServiceUnderTest.readAllAccounts();

        //then
        assertEquals(2, foundList.size());
    }

    @Test
    void shouldThrowExceptionIfAccountListIsEmpty() {
        //given
        List<Account> accountList = new ArrayList<>();

        when(accountRepository.findAll()).thenReturn(accountList);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.readAllAccounts());
    }

    @Test
    void shouldChangeAccountBalance() {
        //given
        Account account = new Account("10101", 0.0, false);
        Double amount = 100.0;

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        accountServiceUnderTest.changeAccountBalance(account.getIban(), amount);

        //then
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWithEmptyAccountChangeAccountBalance() {
        //given
        Account account = new Account("10101", 0.0, false);
        Double amount = 100.0;

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.changeAccountBalance(account.getIban(), amount));
    }

    @Test
    void shouldChangeBalanceIfAccountIsOverdraft() {
        //given
        Account account = new Account("10101", 0.0, true);
        Double amount = -100.0;

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        accountServiceUnderTest.changeAccountBalance(account.getIban(), amount);

        //then
        assertEquals(-100.0, account.getBalance());
    }

    @Test
    void shouldThrowExceptionIfAccountIsNotOverdraft() {
        //given
        Account account = new Account("10101", 0.0, false);
        Double amount = -100.0;

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        //then
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.changeAccountBalance(account.getIban(), amount));
    }

    @Test
    void shouldDeleteAccount() {
        //given
        Account account = new Account("10101", 0.0, false);
        account.setId(1L);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        accountServiceUnderTest.deleteAccount(account.getIban());

        //then
        verify(accountRepository, times(1)).deleteById(account.getId());
    }

    @Test
    void shouldThrowExceptionIfAccountIsEmptyDeleteAccount() {
        //given
        Account account = new Account("10101", 0.0, false);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.empty());

        //when
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.deleteAccount(account.getIban()));
    }

    @Test
    void shouldThrowExceptionIfAccountHasBalance() {
        //given
        Account account = new Account("10101", 10.0, false);

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));

        //when
        //then
        assertThrows(IllegalStateException.class, () -> accountServiceUnderTest.deleteAccount(account.getIban()));
    }


}