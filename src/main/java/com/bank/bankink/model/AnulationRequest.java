package com.bank.bankink.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnulationRequest {

    private Integer idTransaction;
    private Long cardId;


}
