package com.bank_accounts.repositories;

import com.bank_accounts.domain.entities.Holder;
import com.bank_accounts.domain.repositories.HolderRepository;
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
class HolderRepositoryTest {

    @Autowired
    private HolderRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findBySsn() {
        // given
        Holder testHolder = new Holder("John", "McLeond", "0000");
        underTest.save(testHolder);

        // when
        Optional<Holder> testBySsn = underTest.findBySsn(testHolder.getSsn());

        // then
        Assertions.assertThat(testBySsn.get().getSsn()).isEqualTo("0000");

    }
}