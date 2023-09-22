package com.bank.bankink.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="inactivatedCards")
public class InactivatedCardsEntity {

    @Id
    private Long cardId;
    private String cardHolder;
    private String dueDate;
    private String status;
    private double balance;
}
