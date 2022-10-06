package com.bank_accounts.controller;

import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountServiceImpl accountService;

    @Test
    void shouldGetAccountInfo() throws Exception {
        //given
        Account account = createAccount();
        ObjectMapper mapper = new ObjectMapper();

        when(accountService.readAccountInfo(account.getIban())).thenReturn(Optional.of(account));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/account/" + account.getIban())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Account foundAccount = mapper.readValue(content, Account.class);

        //then
        mockMvc.perform(get("/api/account/" + account.getIban())).andExpect(status().isOk()).andReturn();
        assertEquals(account.getIban(), foundAccount.getIban());
        assertEquals(account.getBalance(), foundAccount.getBalance());
        assertEquals(account.getOverdraft(), foundAccount.getOverdraft());
        assertEquals(account.getHolders().size(), 2);
    }

    @Test
    void shouldReturnNoContentIfAccountDoesNotExist() throws Exception {
        //given
        Account account = createAccount();
        ObjectMapper mapper = new ObjectMapper();

        when(accountService.readAccountInfo(account.getIban())).thenReturn(Optional.empty());

        //when
        //then
        mockMvc.perform(get("/api/account/" + account.getIban())).andExpect(status().isNoContent()).andReturn();
    }


    @Test
    void shouldGetAllAccounts() throws Exception {
        //given
        Account account1 = createAccount();
        Account account2 = createAccount();
        List<Account> accountList = List.of(account1, account2);

        ObjectMapper mapper = new ObjectMapper();

        when(accountService.readAllAccounts()).thenReturn(accountList);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/account")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Account[] accounts = mapper.readValue(content, Account[].class);

        //then
        mockMvc.perform(get("/api/account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(accounts.length, 2);
    }

    @Test
    void shouldAddAccount() throws Exception {
        //given
        Account account = createAccount();
        ObjectMapper mapper = new ObjectMapper();

        when(accountService.createAccount(account)).thenReturn(true);

        //when
        //then
        mockMvc.perform(post("/api/account/" + account.getIban())
                        .content(mapper.writeValueAsString(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void shouldWithdrawFunds() throws Exception {
        //given
        Account account = createAccount();
        Double amount = -50.0;
        ObjectMapper mapper = new ObjectMapper();

        when(accountService.changeAccountBalance(account.getIban(), amount)).thenReturn(true);

        //when
        //then
        mockMvc.perform(put("/api/account/withdraw/" + account.getIban())
                .param("amount", String.valueOf(amount))).andExpect(status().isOk()).andReturn();
    }

    @Test
    void shouldDepositFunds() throws Exception {
        //given
        Account account = createAccount();
        Double amount = 50.0;
        ObjectMapper mapper = new ObjectMapper();

        when(accountService.changeAccountBalance(account.getIban(), amount)).thenReturn(true);

        //when
        //then
        mockMvc.perform(put("/api/account/deposit/" + account.getIban())
                .param("amount", String.valueOf(amount))).andExpect(status().isOk()).andReturn();
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        //given
        Account account = createAccount();

        when(accountService.deleteAccount(account.getIban())).thenReturn(true);

        //when
        //then
        mockMvc.perform(delete("/api/account/" + account.getIban()))
                .andExpect(status().isOk()).andReturn();
    }

    private Account createAccount() {
        Account account = new Account("101101", 100.0, true);
        Holder holder1 = new Holder("Luke", "Skywalker", "100100");
        Holder holder2 = new Holder("Leia", "Organa", "200200");
        Set<Holder> holderSet = new HashSet<>();
        holderSet.add(holder1);
        holderSet.add(holder2);
        account.setHolders(holderSet);
        return account;
    }
}