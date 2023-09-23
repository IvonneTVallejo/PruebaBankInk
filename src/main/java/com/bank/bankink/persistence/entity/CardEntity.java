package com.bank.bankink.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cards")
public class CardEntity implements Serializable {

    @Id
    private Long cardId;
    private String cardHolder;
    private String dueDate;
    private String status;
    private double balance;

    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "card")
    @JsonIgnoreProperties("cards")
    private List<TransactionsEntity> transactions;
}
