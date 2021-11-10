package com.example.nbpexchange.model.requestbody;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseExchangeRequestBody {
    List<CurrencyAmount> currencyAmountList;
}
