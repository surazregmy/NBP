package com.example.nbpexchange.repository;

import com.example.nbpexchange.model.MiddleExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MiddleExchangeRepository extends JpaRepository<MiddleExchange,Long> {
    Optional<MiddleExchange> getMiddleExchangeByEffectiveDate(String effectiveDate);
}
