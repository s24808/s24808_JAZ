package pl.pjatk.nbp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.nbp.service.CurrencyService;

@RestController
@Tag(name = "Kontroler Walutowy", description = "Endpointy do zarządzania walutami")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/exchange-rate/{currency}")
    @Operation(summary = "Średni kurs walutowy", description = "Pobierz średni kurs walutowy z ostatnich dni")
    public double getAverageExchangeRate(@PathVariable String currency, @RequestParam(required = false, defaultValue = "1") int numberOfDays) {
        return currencyService.calculateAverageExchangeRate(currency, numberOfDays);
    }
}
