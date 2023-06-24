package pl.pjatk.nbp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.nbp.NbpResponse;
import pl.pjatk.nbp.Rate;
import pl.pjatk.nbp.model.CurrencyQuery;
import pl.pjatk.nbp.repository.CurrencyQueryRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final CurrencyQueryRepository currencyQueryRepository;

    public CurrencyService(CurrencyQueryRepository currencyQueryRepository) {
        this.currencyQueryRepository = currencyQueryRepository;
        this.restTemplate = new RestTemplate();
    }

    public double calculateAverageExchangeRate(String currency, int numberOfDays) {
        // Pobierz dane z API NBP dla danej waluty i liczby dni
        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/A/{currency}/last/{numberOfDays}/?format=json";
        String url = apiUrl.replace("{currency}", currency).replace("{numberOfDays}", String.valueOf(numberOfDays));
        NbpResponse nbpResponse = restTemplate.getForObject(url, NbpResponse.class);

        // Oblicz średni kurs z pobranych danych
        List<Rate> rates = nbpResponse.getRates();
        double sum = rates.stream().mapToDouble(Rate::getMid).sum();
        double averageRate = sum / numberOfDays;

        // Zapisz zapytanie do bazy danych
        CurrencyQuery currencyQuery = new CurrencyQuery();
        currencyQuery.setCurrency(currency);
        currencyQuery.setNumberOfDays(numberOfDays);
        currencyQuery.setExchangeRate(sum);
        currencyQuery.setTimestamp(LocalDate.now().atStartOfDay());
        currencyQuery.setCalculatedRate(averageRate);
        currencyQuery.setQueryDate(LocalDate.now());
        currencyQuery.setQueryTime(LocalTime.now());

        currencyQueryRepository.saveCurrencyQuery(currencyQuery);

        return averageRate;
    }
}
