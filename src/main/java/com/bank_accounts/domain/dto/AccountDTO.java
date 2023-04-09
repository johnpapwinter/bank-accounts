package com.bank_accounts.domain.dto;

import java.time.LocalDateTime;

public record AccountDTO(
        String iban,
        Double balance,
        LocalDateTime dateOpened
) {
}
