package com.example.nbpexchange;


import com.example.nbpexchange.model.Currency;
import com.example.nbpexchange.model.MiddleExchange;
import com.example.nbpexchange.model.Rate;
import com.example.nbpexchange.model.SalesExchange;
import com.example.nbpexchange.repository.CurrencyRepository;
import com.example.nbpexchange.repository.MiddleExchangeRepository;
import com.example.nbpexchange.repository.RateRepository;
import com.example.nbpexchange.repository.SalesExchangeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CacheLoader {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    RateRepository rateRepository;
    @Autowired
    SalesExchangeRepository salesExchangeRepository;
    @Autowired
    MiddleExchangeRepository middleExchangeRepository;

    //Loads only once to initialize data in tables
    @PostConstruct
    public void initialize() {
        loadCtable();
        loadATable();
        loadBTable();
    }

    private void loadATable() {
        String url = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";
        loadTable(url);
    }

    private void loadBTable(){
        String url = "https://api.nbp.pl/api/exchangerates/tables/B?format=json";
        loadTable(url);
    }

    private void loadTable(String url){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body().substring(1,response.body().length()-1));
            JSONArray jsonArray = jsonObject.getJSONArray("rates");

            MiddleExchange middleExchange = new MiddleExchange();
            middleExchange.setNo(jsonObject.getString("no"));
            middleExchange.setEffectiveDate(jsonObject.getString("effectiveDate"));

            MiddleExchange savedMiddleExchange = middleExchangeRepository.save(middleExchange);

            jsonArray.forEach(me-> {
                JSONObject midEx = (JSONObject) me;
                Currency currency = new Currency();
                currency.setCode(midEx.getString("code"));
                currency.setDescription(midEx.getString("currency"));
                //Todo Check if the current already exists
                currencyRepository.save(currency);

                Rate rate = new Rate();
                rate.setMid(midEx.getDouble("mid"));
                rate.setCurrency(currency);
                rate.setMiddleExchange(middleExchange);

                Rate r = rateRepository.save(rate);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadCtable() {

        String url = "https://api.nbp.pl/api/exchangerates/tables/C?format=json";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body().substring(1,response.body().length()-1));
            JSONArray jsonArray = jsonObject.getJSONArray("rates");

            SalesExchange salesExchange = new SalesExchange();
            salesExchange.setNo(jsonObject.getString("no"));
            salesExchange.setTradingDate(jsonObject.getString("tradingDate"));
            salesExchange.setEffectiveDate(jsonObject.getString("effectiveDate"));
            SalesExchange s = salesExchangeRepository.save(salesExchange);

            jsonArray.forEach(se->{
                JSONObject saleEx = (JSONObject) se;
                Currency currency = new Currency();
                currency.setCode(saleEx.getString("code"));
                currency.setDescription(saleEx.getString("currency"));
                System.out.println("Helllo");
                System.out.println(currency.getCode());
                System.out.println(currency.getDescription());
                Currency  c = currencyRepository.save(currency);


                Rate rate  = new Rate();
                rate.setBid(saleEx.getDouble("bid"));
                rate.setAsk(saleEx.getDouble("ask"));
                rate.setCurrency(c);
                rate.setSalesExchange(s);
                Rate r = rateRepository.save(rate);
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
