package com.bank_accounts.repositories;

import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.domain.repositories.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByIban() {
        // given
        Account testAccount = new Account("800900", 10.0, false);
        underTest.save(testAccount);

        // when
        Optional<Account> exists = underTest.findByIban(testAccount.getIban());

        // then
        Assertions.assertThat(exists.get().getIban()).isEqualTo(testAccount.getIban());

    }
}