package com.example.nbpexchange.model.requestbody;

import lombok.Data;

@Data
public class CurrencyAmount {
    private  String currencyCode;
    private  int quantity;
}
