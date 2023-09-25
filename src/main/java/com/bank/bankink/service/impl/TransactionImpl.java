package com.bank.bankink.service.impl;

import com.bank.bankink.model.*;
import com.bank.bankink.persistence.entity.CardEntity;
import com.bank.bankink.persistence.entity.TransactionsEntity;
import com.bank.bankink.persistence.repository.CardRepository;
import com.bank.bankink.persistence.repository.TransactionRepository;
import com.bank.bankink.service.TransactionService;
import com.bank.bankink.utils.Excepcion;
import com.bank.bankink.utils.UtilidadesAplicacion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

import static com.bank.bankink.utils.Constantes.*;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UtilidadesAplicacion utilidadesAplicacion;

    @Override
    public TransactionResponse purchaseTransaction(TransactionRequest request) {
        Optional<CardEntity> entity = cardRepository.findByCardId(request.getCardId());
        if (request.getPrice() > entity.get().getBalance()){
            throw new Excepcion(ERR_06);
        }

        TransactionResponse purchase = transformTransactionResponse(transactionRepository.save(transformTransactionEntity(request)));
        return purchase;
    }

    private TransactionResponse transformTransactionResponse(TransactionsEntity entity) {
        TransactionResponse response = new TransactionResponse();
        BeanUtils.copyProperties(entity, response);
        response.setCardId(entity.getCard().getCardId());
        return response;
    }
    private TransactionsEntity transformTransactionEntity(TransactionRequest request){
        TransactionsEntity entity = new TransactionsEntity();
        entity.setIdTransaction(request.getIdTransaction());
        entity.setPrice((utilidadesAplicacion.price(request.getPrice(),request.getCardId())));
        entity.setFecha(new Date());
        entity.setStatus("Exitoso");

        CardEntity card = new CardEntity();
        card.setCardId(request.getCardId());
        entity.setCard(card);
        return entity;
    }

    @Override
    public TransactionResponse getTransactionInfo(Integer transactionId) {
        Optional<TransactionsEntity> entity = transactionRepository.findById(transactionId);
         if(!entity.isPresent()){
            throw new Excepcion(ERR_07);
        }
        return transformTransactionResponse(entity.get());
    }

    @Override
    public TransactionResponse anulation(AnulationRequest request) {
        Optional<TransactionsEntity> entity = transactionRepository.findById(request.getIdTransaction());
        Optional<CardEntity> entityCard = cardRepository.findByCardId(request.getCardId());
        if(!entity.isPresent()){
            throw new Excepcion(ERR_07);
        }

        TransactionsEntity existingEntity = entity.get();
        CardEntity existingCard = entityCard.get();
        double currentBalance = existingCard.getBalance();
        double newBalance = currentBalance + existingEntity.getPrice();
        existingCard.setBalance(newBalance);
        existingEntity.setStatus("Anulado");

        cardRepository.save(existingCard);
        TransactionsEntity updatedEntity = transactionRepository.save(existingEntity);
        return transformTransactionResponse(updatedEntity);

    }



}
