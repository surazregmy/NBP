package service;

import com.example.nbpexchange.model.Rate;
import com.example.nbpexchange.model.SalesExchange;
import com.example.nbpexchange.repository.RateRepository;
import com.example.nbpexchange.repository.SalesExchangeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class SalesExchangeService {

    Logger logger = LoggerFactory.getLogger(SalesExchangeService.class);

    @Autowired
    SalesExchangeRepository salesExchangeRepository;

    @Autowired
    RateRepository rateRepository;

    public Double findRateByDateAndCurrency(String date, String currency) {
        Optional<SalesExchange> salesExchange = salesExchangeRepository.findSalesExchangeByEffectiveDate(date);
        if (salesExchange.isPresent()) {
            Optional<Rate> rate = rateRepository.getRateBySalesExchangeAndCurrency(salesExchange.get(),currency);
            if (rate.isPresent()) {
                logger.info("found in Cache");
                rate.get().getBid();
            } else {
                fetchThroughAPI();
            }
        } else {
            return fetchThroughAPI();
        }
        return null;
    }

    private Double fetchThroughAPI() {
        logger.info("Not found in Cache! Fetching through the API now.....");
        String url = "http://api.nbp.pl/api/exchangerates/rates/{table}/code}/{date}?format=json";

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());

            JSONArray jsonArray = jsonObject.getJSONArray("rates");
            JSONObject saleEx = jsonArray.getJSONObject(0);
            return saleEx.getDouble("bid");

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
