package io.github.joherrer.cashcards.dto;

import java.math.BigDecimal;

public record TestCashCard(Long id, BigDecimal amount, String owner) {}
