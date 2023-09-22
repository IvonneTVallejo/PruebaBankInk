package com.bank.bankink.persistence.repository;

import com.bank.bankink.persistence.entity.CardEntity;
import com.bank.bankink.persistence.entity.InactivatedCardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InactivatedCardsRepository extends JpaRepository<InactivatedCardsEntity, Long> {

    Optional<InactivatedCardsEntity> findByCardId(Long cardId);
}
