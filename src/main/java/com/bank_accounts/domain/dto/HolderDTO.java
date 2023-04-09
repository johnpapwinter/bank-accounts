package com.bank_accounts.domain.dto;

import java.util.List;

public record HolderDTO(
        String firstname,
        String lastname,
        String ssn,
        List<AccountDTO> accounts
) {
}
