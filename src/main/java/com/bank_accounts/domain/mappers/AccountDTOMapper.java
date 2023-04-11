package com.bank_accounts.domain.mappers;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.entities.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {

    private final HolderDTOMapper holderDTOMapper;

    public AccountDTOMapper(HolderDTOMapper holderDTOMapper) {
        this.holderDTOMapper = holderDTOMapper;
    }

    @Override
    public AccountDTO apply(Account account) {
        return new AccountDTO(
                account.getIban(),
                account.getBalance(),
                account.getDateOpened(),
                account.getHolders()
                        .stream()
                        .map(holderDTOMapper)
                        .collect(Collectors.toList())
        );
    }
}
