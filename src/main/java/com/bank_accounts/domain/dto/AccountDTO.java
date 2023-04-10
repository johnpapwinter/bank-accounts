package com.bank_accounts.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AccountDTO(
        String iban,
        Double balance,
        LocalDateTime dateOpened,
        List<HolderDTO> holders
) {
}
