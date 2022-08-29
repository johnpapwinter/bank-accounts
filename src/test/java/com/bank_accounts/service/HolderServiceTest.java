package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.dao.HolderRepository;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolderServiceTest {

    @Mock
    HolderRepository holderRepository;

    @Mock
    AccountService accountService;

    @InjectMocks
    HolderService holderServiceUnderTest;

    @Test
    void shouldCreateHolder() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.empty());
        when(holderRepository.save(holder)).thenReturn(holder);

        //when
        boolean checkIfTrue = holderServiceUnderTest.createHolder(holder);

        //then
        Mockito.verify(holderRepository, times(1)).save(Mockito.any(Holder.class));
        assertTrue(checkIfTrue);

    }

    @Test
    void shouldReturnFalseIfHolderExists() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        //when
        boolean checkIfTrue = holderServiceUnderTest.createHolder(holder);

        //then
        assertFalse(checkIfTrue);

    }



    @Test
    void shouldReadHolder() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        //when
        Optional<Holder> foundHolder = holderServiceUnderTest.readHolder(holder.getSsn());

        //then
        assertEquals(holder, foundHolder.get());

    }

    @Test
    void shouldReadAllHolders() {
        //given
        Holder holder1 = new Holder("Aang", "Airbender", "112233");
        Holder holder2 = new Holder("Katara", "Waterbender", "445566");
        List<Holder> allHolders = new ArrayList<>();
        allHolders.add(holder1);
        allHolders.add(holder2);

        when(holderRepository.findAll()).thenReturn(allHolders);

        //when
        List<Holder> foundHolders = holderServiceUnderTest.readAllHolders();

        //then
        assertEquals(foundHolders.size(), 2);
    }

    @Test
    void shouldThrowExceptionIfNoHoldersExist() {
        //given
        List<Holder> allHolders = new ArrayList<>();

        when(holderRepository.findAll()).thenReturn(allHolders);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> holderServiceUnderTest.readAllHolders());
    }


    @Test
    void shouldUpdateHolder() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");
        holder.setId(100L);
        Holder updatedHolder = new Holder("Aang", "Avatar", "223344");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));
        ArgumentCaptor<Holder> captor = ArgumentCaptor.forClass(Holder.class);

        //when
        holderServiceUnderTest.updateHolder(holder.getSsn(), updatedHolder);
        Mockito.verify(holderRepository, times(1)).save(captor.capture());
        Holder actual = captor.getValue();

        //then
        assertEquals(actual.getId(), updatedHolder.getId());
        assertEquals(actual.getFirstname(), updatedHolder.getFirstname());
        assertEquals(actual.getLastname(), updatedHolder.getLastname());
        assertEquals(actual.getSsn(), updatedHolder.getSsn());

    }

    @Test
    void shouldReturnFalseIfHolderDoesNotExist() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");
        Holder updatedHolder = new Holder("Aang", "Avatar", "223344");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.empty());

        //when
        boolean checkIfTrue = holderServiceUnderTest.updateHolder(holder.getSsn(), updatedHolder);

        //then
        assertFalse(checkIfTrue);

    }


    @Test
    void shouldDeleteHolder() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");
        holder.setId(100L);

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        //when
        boolean checkIfTrue = holderServiceUnderTest.deleteHolder(holder.getSsn());

        //then
        Mockito.verify(holderRepository, times(1)).deleteById(holder.getId());
        assertTrue(checkIfTrue);
    }

    @Test
    void shouldReturnFalseIfDeletedHolderDoesNotExist() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.empty());

        //when
        boolean checkIfFalse = holderServiceUnderTest.deleteHolder(holder.getSsn());

        //then
        assertFalse(checkIfFalse);
    }

    @Test
    void shouldAddAccount() {
        //given
        Holder holder = new Holder("Aang", "Airbender", "112233");
        Account account = new Account("101101", 100.0, false);

        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));
        when(accountService.readAccountInfo(account.getIban())).thenReturn(Optional.of(account));

        //when
        holderServiceUnderTest.addAccount(account.getIban(), holder.getSsn());

        //then
        Mockito.verify(holderRepository, times(1)).save(holder);
    }
}