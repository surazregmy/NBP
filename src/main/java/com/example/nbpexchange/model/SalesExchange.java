package com.example.nbpexchange.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class SalesExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String no;
    private String tradingDate;
    private String effectiveDate;
    @OneToMany(mappedBy = "salesExchange")
    private List<Rate> rates;
}
