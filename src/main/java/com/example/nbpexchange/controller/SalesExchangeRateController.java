package com.example.nbpexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SalesExchangeService;

@RestController
@RequestMapping("/sell/exchange/")
public class SalesExchangeRateController {


    SalesExchangeService salesExchangeService;

    @GetMapping("{code}/date/{date}")
    public ResponseEntity getSalesExchangeRate(@PathVariable String code, @PathVariable String date){
        Double salesRate =  salesExchangeService.findRateByDateAndCurrency(date,code);
        return ResponseEntity.ok(salesRate);
    }
}
