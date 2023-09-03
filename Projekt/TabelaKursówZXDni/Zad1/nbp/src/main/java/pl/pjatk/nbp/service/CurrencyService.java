package pl.pjatk.nbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.nbp.NbpResponse;
import pl.pjatk.nbp.Rate;
import pl.pjatk.nbp.model.CurrencyQuery;
import pl.pjatk.nbp.model.CurrencyType;
import pl.pjatk.nbp.repository.CurrencyQueryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyQueryRepository currencyQueryRepository;
    private final RestTemplate restTemplate;

    @Autowired

    public CurrencyService(CurrencyQueryRepository currencyQueryRepository, RestTemplate restTemplate) {
        this.currencyQueryRepository = currencyQueryRepository;
        this.restTemplate = restTemplate;
    }

    public void saveCurrencyQuery(String currency) {
        // Pobierz dane z API NBP dla danej waluty
        String apiUrl = "http://api.nbp.pl/api/exchangerates/tables/A/last/7/";
        NbpResponse nbpResponse = restTemplate.getForObject(apiUrl, NbpResponse.class);

        // Wybierz pierwszą tabelę z odpowiedzi
        List<Rate> rates = nbpResponse.getRates().get(0).getRates();

        // Znajdź Rate dla danej waluty
        Optional<Rate> optionalRate = rates.stream()
                .filter(rate -> rate.getCurrency().equalsIgnoreCase(currency))
                .findFirst();

        if (optionalRate.isPresent()) {
            Rate rate = optionalRate.get();
            double exchangeRate = rate.getMid();

            // Zapisz zapytanie do bazy danych
            CurrencyQuery currencyQuery = new CurrencyQuery();
            currencyQuery.setCurrency(CurrencyType.valueOf(currency)); // Ustaw enum Currency
            currencyQuery.setCalculatedRate(exchangeRate);
            currencyQuery.setQueryDate(LocalDate.now());
            currencyQuery.setQueryTime(LocalTime.now());

            currencyQueryRepository.save(currencyQuery);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Brak danych dla waluty: " + currency);
        }
    }
}
