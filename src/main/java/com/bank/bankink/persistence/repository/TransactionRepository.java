package com.bank.bankink.persistence.repository;

import com.bank.bankink.persistence.entity.CardEntity;
import com.bank.bankink.persistence.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionsEntity, Integer> {

    Optional<TransactionsEntity> findByCard(Long cardId);
}
