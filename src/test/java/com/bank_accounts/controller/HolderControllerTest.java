package com.bank_accounts.controller;

import com.bank_accounts.exceptions.HolderAlreadyExistsException;
import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.HolderServiceImpl;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HolderController.class)
class HolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HolderServiceImpl holderService;


    @Test
    void shouldGetHolder() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Holder holder = createHolder();

        when(holderService.readHolder(holder.getSsn())).thenReturn(Optional.of(holder));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/holder/" + holder.getSsn())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Holder foundHolder = objectMapper.readValue(content, Holder.class);

        //then
        mockMvc.perform(get("/api/holder/" + holder.getSsn())).andExpect(status().isOk())
                .andReturn();
        assertEquals(holder.getFirstname(), foundHolder.getFirstname());
        assertEquals(holder.getLastname(), foundHolder.getLastname());
        assertEquals(holder.getSsn(), foundHolder.getSsn());
    }

    @Test
    void shouldReturnNoContentWhenHolderNotExists() throws Exception {
        //given
        Holder holder = createHolder();

        when(holderService.readHolder(holder.getSsn())).thenReturn(Optional.empty());

        //when
        //then
        mockMvc.perform(get("/api/holder/" + holder.getSsn())).andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    void shouldGetAllHolders() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Holder holder1 = createHolder();
        Holder holder2 = createHolder();
        List<Holder> allHolders = List.of(holder1, holder2);

        when(holderService.readAllHolders()).thenReturn(allHolders);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/holder")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Holder[] holders = objectMapper.readValue(content, Holder[].class);

        //then
        mockMvc.perform(get("/api/holder")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(holders.length, 2);
    }

    @Test
    void shouldAddHolder() throws Exception {
        //given
        Holder holder = createHolder();
        ObjectMapper objectMapper = new ObjectMapper();

        when(holderService.createHolder(holder)).thenReturn(true);

        //when
        //then
        mockMvc.perform(post("/api/holder")
                        .content(objectMapper.writeValueAsString(holder))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
   }


    @Test
    void shouldUpdateHolder() throws Exception {
        //given
        Holder holder = createHolder();
        ObjectMapper objectMapper = new ObjectMapper();

        when(holderService.updateHolder(holder.getSsn(), holder)).thenReturn(true);

        //when
        //then
        mockMvc.perform(put("/api/holder/" + holder.getSsn())
                        .content(objectMapper.writeValueAsString(holder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }


    @Test
    void shouldDeleteHolder() throws Exception {
        //given
        Holder holder = createHolder();

        when(holderService.deleteHolder(holder.getSsn())).thenReturn(true);

        //when
        //then
        mockMvc.perform(delete("/api/holder/" + holder.getSsn()))
                .andExpect(status().isOk()).andReturn();
    }



    @Test
    void shouldAddAccountToHolder() throws Exception {
        //given
        Holder holder = new Holder("James", "Bond", "007");
        Account account = new Account("101", 10.0, true);

        doNothing().when(holderService).addAccount(account.getIban(), holder.getSsn());

        //when
        //then
        mockMvc.perform(put("/api/holder/" + holder.getSsn() + "/" + account.getIban()))
                .andExpect(status().isOk()).andReturn();
    }

    private Holder createHolder() {
        Holder holder = new Holder("James", "Bond", "007");
        Account account1 = new Account("101-101", 10.0, false);
        Account account2 = new Account("202-202", 0.0, true);
        Set<Account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        holder.setAccounts(accountSet);

        return holder;
    }
}