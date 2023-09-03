package pl.pjatk.nbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.nbp.GoldPriceData;
import pl.pjatk.nbp.GoldPriceResponse;
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

    public double fetchAndSaveGoldPrice() {
        String url = "http://api.nbp.pl/api/cenyzlota";
        GoldPriceResponse goldPriceResponse = restTemplate.getForObject(url, GoldPriceResponse.class);

        // Oblicz średni kurs z pobranych danych
        List<GoldPriceData> rates = goldPriceResponse.getData();
        double sum = rates.stream().mapToDouble(GoldPriceData::getCena).sum();
        double averageRate = sum / rates.size();

        // Zapisz zapytanie do bazy danych
        CurrencyQuery goldQuery = new CurrencyQuery();
        goldQuery.setCurrency(CurrencyType.GOLD);
        goldQuery.setQueryDate(LocalDate.now());
        goldQuery.setQueryTime(LocalTime.now());
        goldQuery.setCalculatedRate(averageRate);

        currencyQueryRepository.save(goldQuery);

        return averageRate;
    }
}