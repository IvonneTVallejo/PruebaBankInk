package com.bank.bankink.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Integer idTransaction;
    @Column(name = "price")
    private double price;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonIgnoreProperties("transactions")
    private CardEntity card;

}
