package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.dao.HolderRepository;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.exceptions.EntryAlreadyExistsException;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@SpringBootTest
class HolderServiceTest {

    @Mock
    private HolderRepository holderRepository;
    private AutoCloseable autoCloseable;
//    @InjectMocks
    private HolderService holderServiceTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        holderServiceTest =new HolderService(holderRepository);
        createDummyData();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void createHolder() throws EntryAlreadyExistsException {
        // given
        // when
        // that
        Holder testHolder = new Holder("James", "Bond", "007");
        holderServiceTest.createHolder(testHolder);
        ArgumentCaptor<Holder> holderArgumentCaptor = ArgumentCaptor.forClass(Holder.class);
        verify(holderRepository).save(holderArgumentCaptor.capture());
        Holder capturedHolder = holderArgumentCaptor.getValue();

        Assertions.assertThat(capturedHolder.getFirstname()).isEqualTo(testHolder.getFirstname());
        Assertions.assertThat(capturedHolder.getLastname()).isEqualTo(testHolder.getLastname());
        Assertions.assertThat(capturedHolder.getSsn()).isEqualTo(testHolder.getSsn());

    }

    @Test
    void readHolder() {
        Holder h = new Holder();
        h.setFirstname("Bobby");
        h.setLastname("Brown");
        h.setSsn("555000");
        holderRepository.save(h);

        Optional<Holder> retrievedHolder = holderServiceTest.readHolder("555000");
        System.out.println(retrievedHolder);
        assertEquals(h, retrievedHolder.get());

    }


    @Test
    void readAllHolders() {
        // given
        List<Holder> allTestHolders = holderServiceTest.readAllHolders();
        assertEquals(allTestHolders.size(), 2);
        // when
        // that
    }

    @Test
    @Disabled
    void updateHolder() {
        // given
        // when
        // that
    }

    @Test
    @Disabled
    void deleteHolder() {
        // given
        // when
        // that
    }

    @Test
    @Disabled
    void addAccount() {
        // given
        // when
        // that
    }

    private void createDummyData() {
        Holder dummyHolder1 = new Holder("Katara", "Waterbender", "801801");
        Holder dummyHolder2 = new Holder("Aang", "Airbender", "901901");
        holderRepository.save(dummyHolder1);
        holderRepository.save(dummyHolder2);
    }
}