package com.bank.bankink.service.impl;

import com.bank.bankink.model.*;
import com.bank.bankink.persistence.entity.CardEntity;
import com.bank.bankink.persistence.entity.InactivatedCardsEntity;
import com.bank.bankink.persistence.repository.CardRepository;
import com.bank.bankink.persistence.repository.InactivatedCardsRepository;
import com.bank.bankink.service.CardService;
import com.bank.bankink.utils.Excepcion;
import com.bank.bankink.utils.UtilidadesAplicacion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bank.bankink.utils.Constantes.*;

import java.text.ParseException;
import java.util.Optional;

@Service
public class CardImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    InactivatedCardsRepository inactivatedCardsRepository;

    @Autowired
    UtilidadesAplicacion utilidadesAplicacion;


    @Override
    public CardNumberRequest cardNumber(Long productId) throws ParseException {
        CardNumberRequest cardRequest = new CardNumberRequest();
        cardRequest.setCardId(Long.valueOf(utilidadesAplicacion.generateCard((productId))));
        return cardRequest;
    }

    @Override
    public CardResponse activateCard(ActivateRequest request) {
        Optional<CardEntity> entity = cardRepository.findByCardId(request.getCardId());
        Optional<InactivatedCardsEntity> entityInactive = inactivatedCardsRepository.findByCardId(request.getCardId());
        if (entity.isPresent()){
            throw new Excepcion(ERR_01);
        } else if (entityInactive.isPresent()) {
            InactivatedCardsEntity backupCard = inactivatedCardsRepository.findByCardId(request.getCardId()).orElse(null);
            if (backupCard != null) {
                CardEntity originalCard = new CardEntity();
                originalCard.setCardId(backupCard.getCardId());
                originalCard.setCardHolder(backupCard.getCardHolder());
                originalCard.setStatus(backupCard.getStatus());
                originalCard.setDueDate(backupCard.getDueDate());
                originalCard.setStatus("Active");
                cardRepository.save(originalCard);
            }
            inactivatedCardsRepository.deleteById(request.getCardId());
        }
        CardResponse cardActivated = transformCardResponse(cardRepository.save(transformCardEntity(request)));
        return cardActivated;
    }
    private CardResponse transformCardResponse(CardEntity entity) {
        CardResponse response = new CardResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    private CardEntity transformCardEntity(ActivateRequest request){
        CardEntity entity = new CardEntity();
        entity.setCardId(request.getCardId());
        entity.setCardHolder(request.getCardHolder());
        entity.setDueDate(utilidadesAplicacion.dueDate());
        entity.setStatus("Active");
        entity.setBalance(0);
        return entity;
    }

    @Override
    public void inactivateCard(Long cardId) {
        Optional<CardEntity> entity = cardRepository.findByCardId(cardId);
        Optional<InactivatedCardsEntity> entityInactive = inactivatedCardsRepository.findByCardId(cardId);
        if (entityInactive.isPresent()){
            throw new Excepcion(ERR_03);
        } else if (!entity.isPresent()){
            throw new Excepcion(ERR_02);
        } else {
            CardEntity originalCard = cardRepository.findByCardId(cardId).orElse(null);
            if (originalCard != null) {
                InactivatedCardsEntity backupCard = new InactivatedCardsEntity();
                backupCard.setCardId(originalCard.getCardId());
                backupCard.setCardHolder(originalCard.getCardHolder());
                backupCard.setStatus(originalCard.getStatus());
                backupCard.setDueDate(originalCard.getDueDate());
                backupCard.setStatus("Inactive");
                inactivatedCardsRepository.save(backupCard);
            }
            cardRepository.deleteById(cardId);
        }
    }

    @Override
    public CardResponse topUpBalance(BalanceRequest request) {
        Optional<CardEntity> entity = cardRepository.findByCardId(request.getCardId());
        Optional<InactivatedCardsEntity> entityInactive = inactivatedCardsRepository.findByCardId(request.getCardId());
        if (entityInactive.isPresent()){
            throw new Excepcion(ERR_04);
        } else if(!entity.isPresent()){
            throw new Excepcion(ERR_02);
        }

        CardEntity existingEntity = entity.get();
        double currentBalance = existingEntity.getBalance();
        double newBalance = currentBalance + request.getBalance();
        existingEntity.setBalance(newBalance);

        CardEntity updatedEntity = cardRepository.save(existingEntity);
        CardResponse cardRecharged = transformCardResponse(updatedEntity);
        return cardRecharged;

    }

}
