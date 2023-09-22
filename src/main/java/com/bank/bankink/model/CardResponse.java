package com.bank.bankink.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {

    private Long cardId;
    private String cardHolder;
    private String dueDate;
    private String status;
    private double balance;
}
