package com.bank.bankink.controller;

import com.bank.bankink.model.*;
import com.bank.bankink.service.TransactionService;
import com.bank.bankink.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import static com.bank.bankink.utils.Constantes.PURCHASE;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    // Método para realizar una transaccion de compra
    @PostMapping(value = "/purchase", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<TransactionResponse>> purchaseTransaction(@RequestBody TransactionRequest entity){
        return new ResponseEntity<>(new Response<>(transactionService.purchaseTransaction(entity), PURCHASE), HttpStatus.OK);
    }

    // Método para consultar la transaccion
    @GetMapping(value = "/{transactionId}")
    public Optional<TransactionResponse> getTransactionInfo(@PathVariable Integer transactionId){
        return Optional.ofNullable(transactionService.getTransactionInfo(transactionId));
    }

    // Método para anular la transaccion
    @PostMapping(value = "/anulation", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response<TransactionResponse>> anulation(@RequestBody AnulationRequest entity){
        return new ResponseEntity<>(new Response<>(transactionService.anulation(entity), PURCHASE), HttpStatus.OK);
    }
}
