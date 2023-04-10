package com.bank_accounts.domain.exceptions;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message,
        LocalDateTime dateTime
) {
}
