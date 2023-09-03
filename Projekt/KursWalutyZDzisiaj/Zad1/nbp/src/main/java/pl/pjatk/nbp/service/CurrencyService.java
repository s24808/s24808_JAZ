package pl.pjatk.nbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.nbp.NbpResponse;
import pl.pjatk.nbp.Rate;
import pl.pjatk.nbp.model.CurrencyQuery;
import pl.pjatk.nbp.model.CurrencyType;
import pl.pjatk.nbp.repository.CurrencyQueryRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyQueryRepository currencyQueryRepository;
    private final RestTemplate restTemplate;

    @Autowired

    public CurrencyService(CurrencyQueryRepository currencyQueryRepository, RestTemplate restTemplate) {
        this.currencyQueryRepository = currencyQueryRepository;
        this.restTemplate = restTemplate;
    }

    public double getCurrentExchangeRate(CurrencyType currency) {
        // Pobierz aktualny kurs waluty z API NBP
        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/A/{code}/today";
        String url = apiUrl.replace("{code}", currency.name());
        NbpResponse nbpResponse = restTemplate.getForObject(url, NbpResponse.class);

        // Zapisz zapytanie do bazy danych
        CurrencyQuery currencyQuery = new CurrencyQuery();
        currencyQuery.setCurrency(currency);
        currencyQuery.setQueryDate(LocalDate.now());
        currencyQuery.setCalculatedRate(nbpResponse.getRates().get(0).getMid());
        currencyQuery.setQueryDate(LocalDate.now());
        currencyQuery.setQueryTime(LocalTime.now());

        currencyQueryRepository.save(currencyQuery);

        return nbpResponse.getRates().get(0).getMid();
    }
}
