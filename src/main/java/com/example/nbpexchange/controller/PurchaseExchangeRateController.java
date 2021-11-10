package com.example.nbpexchange.controller;

import com.example.nbpexchange.model.requestbody.PurchaseExchangeRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MiddleExchangeService;

@RestController
@RequestMapping("/purchase/exchange/")
public class PurchaseExchangeRateController {

    @PostMapping()
    public ResponseEntity getSalesExchangeRate(@RequestBody PurchaseExchangeRequestBody purchaseExchangeRequestBody){
        return ResponseEntity.ok("Done");
    }
}
