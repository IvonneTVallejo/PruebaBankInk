package com.bank.bankink.controller;

import com.bank.bankink.model.*;
import com.bank.bankink.service.CardService;
import com.bank.bankink.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

import static com.bank.bankink.utils.Constantes.*;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;


    // Método para generar el numero de tarjeta
    @GetMapping(value = "/{productId}/number", produces = { MediaType.APPLICATION_JSON_VALUE })
    public CardNumberRequest getCardNumber(@PathVariable Long productId) throws ParseException {
        return cardService.cardNumber(productId);
    }

    // Método para Activar la tarjeta
    @PostMapping(value = "/enroll", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<CardResponse>> activateCard(@RequestBody ActivateRequest entity){
        return new ResponseEntity<>(new Response<>(cardService.activateCard(entity), CARD_ACTIVATION), HttpStatus.OK);
    }

    // Método para bloquear la tarjeta
    @DeleteMapping(value = "/{cardId}")
    public ResponseEntity<Response<CardResponse>> inactivateCard(@Validated @PathVariable Long cardId){
        cardService.inactivateCard(cardId);
        return new ResponseEntity<>(new Response<>(null, CARD_INACTIVATION), HttpStatus.OK);
    }

    // Método para Recargar saldo en la tarjeta
    @PostMapping(value = "/balance", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<CardResponse>> topUpBalance(@RequestBody BalanceRequest entity){
        return new ResponseEntity<>(new Response<>(cardService.topUpBalance(entity), TOP_UP_BALANCE), HttpStatus.OK);
    }

    // Método para consultar saldo en la tarjeta
    @GetMapping(value = "/balance/{cardId}")
    public Optional<BalanceResponse> getBalance(@PathVariable Long cardId){
        return Optional.ofNullable(cardService.getBalance(cardId));
    }

}
