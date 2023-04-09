package com.bank_accounts.domain.mappers;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.entities.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {

    @Override
    public AccountDTO apply(Account account) {
        return new AccountDTO(account.getIban(), account.getBalance(), account.getDateOpened());
    }
}
