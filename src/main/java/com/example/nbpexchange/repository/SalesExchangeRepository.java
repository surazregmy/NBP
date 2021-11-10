package com.example.nbpexchange.repository;

import com.example.nbpexchange.model.SalesExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesExchangeRepository extends JpaRepository<SalesExchange, Long> {
    Optional<SalesExchange> findSalesExchangeByEffectiveDate(String effectiveDate);
}
