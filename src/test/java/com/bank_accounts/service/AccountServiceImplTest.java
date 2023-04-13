package com.bank_accounts.service;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.domain.entities.Holder;
import com.bank_accounts.domain.exceptions.AccountDoesNotExistException;
import com.bank_accounts.domain.exceptions.HolderAlreadyInAccountException;
import com.bank_accounts.domain.exceptions.InsufficientFundsException;
import com.bank_accounts.domain.exceptions.SingleHolderAccountException;
import com.bank_accounts.domain.mappers.AccountDTOMapper;
import com.bank_accounts.domain.repositories.AccountRepository;
import com.bank_accounts.domain.repositories.HolderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private HolderRepository holderRepository;

    @Mock
    private AccountDTOMapper accountDTOMapper;

    @InjectMocks
    private AccountServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Open up a new account")
    void shouldOpenAccount() {
        // given
        AccountDTO accountDTO = new AccountDTO("GR100-100", 0.0, LocalDateTime.now(), new ArrayList<>());

        when(accountRepository.findByIban(accountDTO.iban())).thenReturn(Optional.empty());
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        serviceUnderTest.openAccount(accountDTO);
        Mockito.verify(accountRepository, times(1)).save(captor.capture());
        Account actual = captor.getValue();

        // then
        assertEquals(accountDTO.iban(), actual.getIban());
        assertEquals(accountDTO.balance(), actual.getBalance());
    }

    @Test
    @DisplayName("Should find an account by IBAN")
    void shouldGetAccountByIban() {
        // given
        Account account = new Account();
        account.setIban("GR100-100");
        account.setBalance(0.0);
        account.setDateOpened(LocalDateTime.now());

        AccountDTO accountDTO = new AccountDTO("GR100-100", 0.0, LocalDateTime.now(), new ArrayList<>());

        when(accountRepository.findByIban(account.getIban())).thenReturn(Optional.of(account));
        when(accountDTOMapper.apply(account)).thenReturn(accountDTO);

        // when
        AccountDTO result = serviceUnderTest.getAccountByIban(account.getIban());

        // then
        assertEquals(account.getIban(), result.iban());
        assertEquals(account.getBalance(), result.balance());
    }

    @Test
    @DisplayName("Should throw exception when IBAN does not exist")
    void shouldThrowExceptionOnFindByIban() {
        // given
        String iban = "GR100-100";

        when(accountRepository.findByIban(iban)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(AccountDoesNotExistException.class, () -> serviceUnderTest.getAccountByIban(iban));
    }

    @Test
    @DisplayName("Should find an account by ID")
    void getAccountById() {
        // given
        Account account = new Account();
        account.setId(1L);
        account.setIban("GR100-100");
        account.setBalance(0.0);
        account.setDateOpened(LocalDateTime.now());

        AccountDTO accountDTO = new AccountDTO("GR100-100", 0.0, LocalDateTime.now(), new ArrayList<>());

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(accountDTOMapper.apply(account)).thenReturn(accountDTO);

        // when
        AccountDTO result = serviceUnderTest.getAccountById(account.getId());

        // then
        assertEquals(account.getIban(), result.iban());
        assertEquals(account.getBalance(), result.balance());
        assertEquals(account.getDateOpened(), result.dateOpened());
    }

    @Test
    @DisplayName("Should throw exception when ID does not exist")
    void shouldThrowExceptionOnFindById() {
        // given
        Long id = 1L;

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(AccountDoesNotExistException.class, () -> serviceUnderTest.getAccountById(id));
    }

    @Test
    @DisplayName("Should return all accounts")
    void shouldFetchAllAccounts() {
        // given
        Account account1 = new Account();
        account1.setId(1L);
        account1.setIban("GR100-100");
        account1.setBalance(0.0);
        account1.setDateOpened(LocalDateTime.now());

        Account account2 = new Account();
        account2.setId(2L);
        account2.setIban("GR200-200");
        account2.setBalance(0.0);
        account2.setDateOpened(LocalDateTime.now());

        Account account3 = new Account();
        account3.setId(3L);
        account3.setIban("GR300-300");
        account3.setBalance(0.0);
        account3.setDateOpened(LocalDateTime.now());

        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);

        AccountDTO accountDTO1 = new AccountDTO("GR100-100", 0.0, LocalDateTime.now(), new ArrayList<>());
        AccountDTO accountDTO2 = new AccountDTO("GR200-200", 0.0, LocalDateTime.now(), new ArrayList<>());
        AccountDTO accountDTO3 = new AccountDTO("GR300-300", 0.0, LocalDateTime.now(), new ArrayList<>());

        when(accountRepository.findAll()).thenReturn(accountList);
        when(accountDTOMapper.apply(account1)).thenReturn(accountDTO1);
        when(accountDTOMapper.apply(account2)).thenReturn(accountDTO2);
        when(accountDTOMapper.apply(account3)).thenReturn(accountDTO3);

        // when
        List<AccountDTO> results = serviceUnderTest.fetchAllAccounts();

        // then
        assertEquals(accountList.size(), results.size());
    }

    @Test
    @DisplayName("Should return all accounts paginated")
    void shouldGetAllAccounts() {
        // given
        Account account1 = new Account();
        account1.setId(1L);
        account1.setIban("GR100-100");
        account1.setBalance(0.0);
        account1.setDateOpened(LocalDateTime.now());

        Account account2 = new Account();
        account2.setId(2L);
        account2.setIban("GR200-200");
        account2.setBalance(0.0);
        account2.setDateOpened(LocalDateTime.now());

        Account account3 = new Account();
        account3.setId(3L);
        account3.setIban("GR300-300");
        account3.setBalance(0.0);
        account3.setDateOpened(LocalDateTime.now());

        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);

        AccountDTO accountDTO1 = new AccountDTO("GR100-100", 0.0, LocalDateTime.now(), new ArrayList<>());
        AccountDTO accountDTO2 = new AccountDTO("GR200-200", 0.0, LocalDateTime.now(), new ArrayList<>());
        AccountDTO accountDTO3 = new AccountDTO("GR300-300", 0.0, LocalDateTime.now(), new ArrayList<>());

        Pageable pageable = PageRequest.of(0, 3);
        Page<Account> accountPage = new PageImpl<>(accountList);

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);
        when(accountDTOMapper.apply(account1)).thenReturn(accountDTO1);
        when(accountDTOMapper.apply(account2)).thenReturn(accountDTO2);
        when(accountDTOMapper.apply(account3)).thenReturn(accountDTO3);

        // when
        Page<AccountDTO> results = serviceUnderTest.getAllAccounts(pageable);

        // then
        assertEquals(3, results.getSize());
    }

    @Test
    @DisplayName("Should deposit funds to account")
    void shouldDepositFunds() {
        // given
        Account account = new Account();
        account.setId(1L);
        account.setBalance(10.0);

        Double amount = 10.5;

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // when
        serviceUnderTest.depositFunds(account.getId(), amount);

        // then
        assertEquals(20.5, account.getBalance());
    }

    @Test
    @DisplayName("Should withdraw funds")
    void shouldWithdrawFunds() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(10.0);

        Double amount = 5.5;

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // when
        serviceUnderTest.withdrawFunds(account.getId(), amount);

        // then
        assertEquals(4.5, account.getBalance());
    }

    @Test
    @DisplayName("Should withdraw funds from overdraft account")
    void shouldWithdrawFundsFromOverdraftAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(10.0);
        account.setOverdraft(true);

        Double amount = 15.5;

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // when
        serviceUnderTest.withdrawFunds(account.getId(), amount);

        // then
        assertEquals(-5.5, account.getBalance());
    }

    @Test
    @DisplayName("Should throw exception when withdrawing more funds from a non-overdraft account")
    void shouldThrowExceptionOnWithdrawFromNotOverdraft() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(10.0);
        account.setOverdraft(false);

        Double amount = 15.5;

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // when
        // then
        assertThrows(InsufficientFundsException.class, () -> serviceUnderTest.withdrawFunds(account.getId(), amount));
    }

    @Test
    @DisplayName("Should assign a holder to an account")
    void shouldAssignHolderToAccount() {
        // given
        Account account = new Account();
        account.setId(1L);

        Holder holder = new Holder();
        holder.setId(1L);
        holder.setSsn("AA-BB");

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        // when
        serviceUnderTest.assignHolderToAccount(account.getId(), holderDTO);

        // then
        assertTrue(account.getHolders().contains(holder));
    }

    @Test
    @DisplayName("Should throw exception if a holder is already assigned to an account")
    void shouldThrowOnAssignIfHolderAlreadyAssigned() {
        // given
        Account account = new Account();
        account.setId(1L);

        Holder holder = new Holder();
        holder.setId(1L);
        holder.setSsn("AA-BB");

        account.getHolders().add(holder);

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        // when
        // then
        assertThrows(HolderAlreadyInAccountException.class,
                () -> serviceUnderTest.assignHolderToAccount(account.getId(), holderDTO));
    }

    @Test
    @DisplayName("Should remove a holder to an account")
    void shouldRemoveHolderFromAccount() {
        // given
        Account account = new Account();
        account.setId(1L);

        Holder holder = new Holder();
        holder.setId(1L);
        holder.setSsn("AA-BB");

        Holder holder2 = new Holder();
        holder2.setId(2L);
        holder2.setSsn("CC-DD");

        account.getHolders().add(holder);
        account.getHolders().add(holder2);

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        // when
        serviceUnderTest.removeHolderFromAccount(account.getId(), holderDTO);

        // then
        assertFalse(account.getHolders().contains(holder));
    }

    @Test
    @DisplayName("Should throw exception if single holder account")
    void shouldThrowIfOnlyOneHolderOnRemove() {
        // given
        Account account = new Account();
        account.setId(1L);

        Holder holder = new Holder();
        holder.setId(1L);
        holder.setSsn("AA-BB");

        account.getHolders().add(holder);

        HolderDTO holderDTO = new HolderDTO("Obi-Wan", "Kenobi", "AA-BB");

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(holderRepository.findBySsn(holder.getSsn())).thenReturn(Optional.of(holder));

        // when
        // then
        assertThrows(SingleHolderAccountException.class,
                () -> serviceUnderTest.removeHolderFromAccount(account.getId(), holderDTO));
    }

    @Test
    @DisplayName("Should change overdraft status of account")
    void shouldToggleOverDraft() {
        // given
        Account account = new Account();
        account.setId(1L);
        account.setOverdraft(false);

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // when
        serviceUnderTest.toggleOverDraft(account.getId());

        // then
        assertEquals(true, account.getOverdraft());
    }

    @Test
    @DisplayName("Should delete an account")
    void shouldDeleteAccount() {
        // given
        Account account = new Account();
        account.setId(1L);

        // when
        serviceUnderTest.deleteAccount(account.getId());

        // then
        Mockito.verify(accountRepository, times(1)).deleteById(account.getId());
    }
}