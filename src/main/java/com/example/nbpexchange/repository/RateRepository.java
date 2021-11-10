package com.example.nbpexchange.repository;

import com.example.nbpexchange.model.MiddleExchange;
import com.example.nbpexchange.model.Rate;
import com.example.nbpexchange.model.SalesExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> getRateBySalesExchangeAndCurrency(SalesExchange salesExchange,String currency);
    Optional<Rate> getRateByMiddleExchangeAAndCurrency(MiddleExchange middleExchange,String currency);
}
