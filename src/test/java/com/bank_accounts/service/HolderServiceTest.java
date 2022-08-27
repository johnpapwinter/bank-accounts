package com.bank_accounts.service;

import com.bank_accounts.dao.HolderRepository;
import com.bank_accounts.model.Holder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolderServiceTest {

    @Mock
    HolderRepository holderRepository;

    @InjectMocks
    HolderService holderServiceUnderTest;

    @Test
    void createHolder() {
        //given
        //when
        //then
    }

    @Test
    void readHolder() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        //when
        Optional<Holder> foundHolder = holderServiceUnderTest.readHolder(holder.getSsn());

        //then
        assertEquals(holder, foundHolder.get());

    }

    @Test
    void readAllHolders() {
        //given
        //when
        //then
    }

    @Test
    void updateHolder() {
        //given
        //when
        //then
    }

    @Test
    void deleteHolder() {
        //given
        //when
        //then
    }

    @Test
    void addAccount() {
        //given
        //when
        //then
    }
}