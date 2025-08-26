package io.github.joherrer.cashcards.repository;

import io.github.joherrer.cashcards.model.CashCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashCardRepository extends JpaRepository<CashCardEntity, Long> {
    Optional<CashCardEntity> findByIdAndOwner(Long id, String owner);
    Page<CashCardEntity> findByOwner(String owner, Pageable pageable);
}
