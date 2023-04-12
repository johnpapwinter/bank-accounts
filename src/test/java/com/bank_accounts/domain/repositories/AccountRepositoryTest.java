package com.bank_accounts.domain.repositories;

import com.bank_accounts.domain.entities.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository repositoryUnderTest;

    @AfterEach
    void tearDown() {
        repositoryUnderTest.deleteAll();
    }

    @Test
    @DisplayName("Should find an account by IBAN")
    void shouldFindByIban() {
        // given
        Account account = new Account();
        account.setId(1L);
        account.setIban("GR100-100-100");
        account.setDateOpened(LocalDateTime.now());
        account.setBalance(0.0);
        account.setOverdraft(false);

        repositoryUnderTest.save(account);

        // when
        Optional<Account> result = repositoryUnderTest.findByIban(account.getIban());

        // then
        assertEquals(account.getId(), result.get().getId());
        assertEquals(account.getIban(), result.get().getIban());
        assertEquals(account.getDateOpened(), result.get().getDateOpened());
        assertEquals(account.getBalance(), result.get().getBalance());
        assertEquals(account.getOverdraft(), result.get().getOverdraft());

    }
}