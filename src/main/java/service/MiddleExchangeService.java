package service;

import com.example.nbpexchange.model.MiddleExchange;
import com.example.nbpexchange.model.Rate;
import com.example.nbpexchange.model.requestbody.CurrencyAmount;
import com.example.nbpexchange.model.requestbody.PurchaseExchangeRequestBody;
import com.example.nbpexchange.repository.MiddleExchangeRepository;
import com.example.nbpexchange.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MiddleExchangeService {
    @Autowired
    RateRepository rateRepository;

    @Autowired
    MiddleExchangeRepository middleExchangeRepository;
    public Double getTotalPurchasing(PurchaseExchangeRequestBody purchaseExchangeRequestBody){
        Double totalAmount = 0.0d;
       for(CurrencyAmount currencyAmount: purchaseExchangeRequestBody.getCurrencyAmountList()){
           Optional<MiddleExchange> middleExchange = middleExchangeRepository.getMiddleExchangeByEffectiveDate(LocalDate.now().toString());
           if(middleExchange.isPresent()){
               Optional<Rate> rate = rateRepository.getRateByMiddleExchangeAAndCurrency(middleExchange.get(),currencyAmount.getCurrencyCode());
               if(rate.isPresent()){
                    totalAmount = totalAmount + rate.get().getBid();
               }
           }
       }
       return totalAmount;
    }
}
