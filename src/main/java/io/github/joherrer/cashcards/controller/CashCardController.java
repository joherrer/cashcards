package io.github.joherrer.cashcards.controller;

import io.github.joherrer.cashcards.dto.CashCard;
import io.github.joherrer.cashcards.model.CashCardEntity;
import io.github.joherrer.cashcards.repository.CashCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cashcards")
class CashCardController {
    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository repository) {
        this.cashCardRepository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard> findById(@PathVariable Long id, Principal principal) {
        return cashCardRepository.findByIdAndOwner(id, principal.getName())
                .map(cashCardEntity -> ResponseEntity.ok(cashCardEntity.toModel()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder ucb, Principal principal) {
        CashCardEntity cashCardEntity = new CashCardEntity(null, cashCard.amount(), principal.getName());
        CashCardEntity savedCashCardEntity = cashCardRepository.save(cashCardEntity);
        URI location = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCardEntity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCardEntity> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                ));
        List<CashCard> cashCards = page.getContent()
                .stream()
                .map(CashCardEntity::toModel)
                .toList();
        return ResponseEntity.ok(cashCards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putCashCard(@PathVariable Long id, @RequestBody CashCard cashCardUpdate, Principal principal) {
        return cashCardRepository.findByIdAndOwner(id, principal.getName())
                .map(cashCardEntity -> {
                    CashCardEntity updatedCashCard = new CashCardEntity(cashCardEntity.getId(), cashCardUpdate.amount(), principal.getName());
                    cashCardRepository.save(updatedCashCard);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        return cashCardRepository.findByIdAndOwner(id, principal.getName())
                .map(cashCardEntity -> {
                    cashCardRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
