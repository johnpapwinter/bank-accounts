package com.bank_accounts.domain.repositories;

import com.bank_accounts.domain.entities.Holder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HolderRepositoryTest {

    @Autowired
    HolderRepository repositoryUnderTest;

    @AfterEach
    void tearDown() {
        repositoryUnderTest.deleteAll();
    }

    @Test
    @DisplayName("Should find a Holder by SSN")
    void shouldFindBySsn() {
        // given
        Holder holder = new Holder();
        holder.setId(1L);
        holder.setFirstname("Obi-Wan");
        holder.setLastname("Kenobi");
        holder.setSsn("AA-BB");

        repositoryUnderTest.save(holder);

        // when
        Optional<Holder> result = repositoryUnderTest.findBySsn(holder.getSsn());

        // then
        assertEquals(holder.getId(), result.get().getId());
        assertEquals(holder.getFirstname(), result.get().getFirstname());
        assertEquals(holder.getLastname(), result.get().getLastname());
        assertEquals(holder.getSsn(), result.get().getSsn());
    }
}