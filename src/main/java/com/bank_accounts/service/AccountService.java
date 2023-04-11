package com.bank_accounts.service;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.dto.HolderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {

    void openAccount(AccountDTO accountDTO);

    AccountDTO getAccountByIban(String iban);

    AccountDTO getAccountById(Long id);

    List<AccountDTO> fetchAllAccounts();

    Page<AccountDTO> getAllAccounts(Pageable pageable);

    void depositFunds(Long id, Double amount);

    void withdrawFunds(Long id, Double amount);

    void assignHolderToAccount(Long id, HolderDTO holderDTO);

    void removeHolderFromAccount(Long id, HolderDTO holderDTO);

    void toggleOverDraft(Long id);

    void deleteAccount(Long id);

}
