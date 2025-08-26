package io.github.joherrer.cashcards.model;

import io.github.joherrer.cashcards.dto.CashCard;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "cash_cards")
public class CashCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Positive
    private BigDecimal amount;

    @Column(nullable = false)
    @NotBlank
    private String owner;

    protected CashCardEntity() {
    }

    public CashCardEntity(Long id, BigDecimal amount, String owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    public Long getId() { return id; }

    public BigDecimal getAmount() { return amount; }

    public String getOwner() { return owner; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public void setOwner(String owner) { this.owner = owner; }

    public CashCard toModel() { return new CashCard(id, amount, owner); }

    public static CashCardEntity fromModel(CashCard cashCard, String owner) {
        return new CashCardEntity(cashCard.id(), cashCard.amount(), owner);
    }
}
