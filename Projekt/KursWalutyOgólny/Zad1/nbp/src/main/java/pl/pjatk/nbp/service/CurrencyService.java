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

    public double calculateAverageExchangeRate(CurrencyType currency) {
        // Pobierz dane z API NBP dla danej waluty i tabeli (np. tabela A)
        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/A/{currency}/?format=json";
        String url = apiUrl.replace("{currency}", currency.name());
        NbpResponse nbpResponse = restTemplate.getForObject(url, NbpResponse.class);

        // Oblicz średni kurs z pobranych danych
        List<Rate> rates = nbpResponse.getRates();
        double sum = rates.stream().mapToDouble(Rate::getMid).sum();
        double averageRate = sum / rates.size();

        // Zapisz zapytanie do bazy danych
        CurrencyQuery currencyQuery = new CurrencyQuery();
        currencyQuery.setCurrency(currency);
        currencyQuery.setCalculatedRate(averageRate);
        currencyQuery.setQueryDate(LocalDate.now());
        currencyQuery.setQueryTime(LocalTime.now());

        currencyQueryRepository.save(currencyQuery);

        return averageRate;
    }
}
