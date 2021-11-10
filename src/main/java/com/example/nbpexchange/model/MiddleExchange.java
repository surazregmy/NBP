package com.example.nbpexchange.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class MiddleExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String no;
    private String effectiveDate;
    @OneToMany(mappedBy = "middleExchange")
    List<Rate> rates;
}
