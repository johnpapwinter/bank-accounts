package com.bank_accounts.entities;

import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.domain.entities.Holder;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class HolderTest {

    @Test
    void shouldAddAccount() {
        //given
        Holder holder = new Holder();
        Account account1 = new Account("101800", 10.0, false);
        Account account2 = new Account("201100", 20.0, false);

        //when
        holder.addAccount(account1);
        holder.addAccount(account2);

        //then
        assertEquals(holder.getAccounts().size(), 2);
    }

    @Test
    void shouldReturnFalseWithNullAccount() {
        //given
        Holder holder = new Holder();
        Account account = null;

        //when
        boolean checkIfFalse = holder.addAccount(account);

        //then
        assertFalse(checkIfFalse);
    }

    @Test
    void shouldThrowExceptionIfAccountHasHolder() {
        //given
        Holder holder = new Holder("James", "Bond", "007");
        Account account = new Account("101800", 10.0, false);
        Set<Holder> holderSet = new HashSet<>();
        holderSet.add(holder);
        account.setHolders(holderSet);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> holder.addAccount(account));
    }



    @Test
    void shouldRemoveAccount() {
        //given
        Holder holder = new Holder("James", "Bond", "007");
        Account account = new Account("101800", 10.0, false);
        holder.addAccount(account);

        //when
        holder.removeAccount(account);

        //then
        assertEquals(holder.getAccounts().size(), 0);
        assertEquals(account.getHolders().size(),0);
    }

    @Test
    void shouldReturnFalseWithNullAccountToRemove() {
        //given
        Holder holder = new Holder();
        Account account = null;

        //when
        boolean checkIfFalse = holder.removeAccount(account);

        //then
        assertFalse(checkIfFalse);
    }

    @Test
    void shouldThrowExceptionIfAccountDoesNotExist() {
        //given
        Holder holder = new Holder("James", "Bond", "007");
        Account account = new Account("101800", 10.0, false);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> holder.removeAccount(account));
    }

}