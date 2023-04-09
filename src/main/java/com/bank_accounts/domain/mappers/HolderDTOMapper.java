package com.bank_accounts.domain.mappers;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Holder;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HolderDTOMapper implements Function<Holder, HolderDTO> {

    private final AccountDTOMapper accountDTOMapper;

    public HolderDTOMapper(AccountDTOMapper accountDTOMapper) {
        this.accountDTOMapper = accountDTOMapper;
    }

    @Override
    public HolderDTO apply(Holder holder) {
        return new HolderDTO(
                holder.getFirstname(),
                holder.getLastname(),
                holder.getSsn(),
                holder.getAccounts()
                        .stream()
                        .map(accountDTOMapper)
                        .collect(Collectors.toList())
        );
    }
}
