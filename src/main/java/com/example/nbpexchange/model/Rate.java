package com.example.nbpexchange.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    Double bid;
    Double ask;
    Double mid;
    @OneToOne
    Currency currency;
    @ManyToOne
    SalesExchange salesExchange;
    @ManyToOne
    MiddleExchange middleExchange;
}
