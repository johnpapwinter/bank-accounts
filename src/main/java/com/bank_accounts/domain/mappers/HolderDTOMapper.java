package com.bank_accounts.domain.mappers;

import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Holder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HolderDTOMapper implements Function<Holder, HolderDTO> {

    @Override
    public HolderDTO apply(Holder holder) {
        return new HolderDTO(
                holder.getFirstname(),
                holder.getLastname(),
                holder.getSsn()
        );
    }
}
