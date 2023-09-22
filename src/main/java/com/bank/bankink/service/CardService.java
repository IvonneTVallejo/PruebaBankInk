package com.bank.bankink.service;

import com.bank.bankink.model.*;

import java.text.ParseException;

public interface CardService {

    CardNumberRequest cardNumber(Long productId) throws ParseException;

    CardResponse activateCard(ActivateRequest request);

    void inactivateCard(Long cardId);

    CardResponse topUpBalance(BalanceRequest request);
}
