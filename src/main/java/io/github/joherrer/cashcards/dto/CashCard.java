package io.github.joherrer.cashcards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CashCard(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String owner
) {}
