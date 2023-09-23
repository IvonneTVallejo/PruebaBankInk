package com.bank.bankink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Integer idTransaction;
    private double price;
    private Date fecha;
    private Long cardId;
    private String status;
}
