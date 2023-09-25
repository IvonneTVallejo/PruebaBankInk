package com.bank.bankink.service;

import com.bank.bankink.model.*;

public interface TransactionService {

    TransactionResponse purchaseTransaction(TransactionRequest request);

    TransactionResponse getTransactionInfo(Integer transactionId);

    TransactionResponse anulation(AnulationRequest request);

}
