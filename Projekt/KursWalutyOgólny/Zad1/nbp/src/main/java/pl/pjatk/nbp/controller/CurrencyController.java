package pl.pjatk.nbp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.nbp.model.CurrencyType;
import pl.pjatk.nbp.service.CurrencyService;

import java.time.LocalDate;

@RestController
@Tag(name = "Kontroler Walutowy", description = "Endpointy do zarządzania walutami")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/exchange-rate/{currency}")
    @Operation(summary = "Średni kurs walutowy na dzień dzisiejszy", description = "Pobierz średni kurs walutowy na dzień dzisiejszy")
    public ResponseEntity<Double> getAverageExchangeRateForToday(
            @PathVariable("currency") CurrencyType currency) {
        double averageRate = currencyService.calculateAverageExchangeRate(currency);
        return ResponseEntity.ok(averageRate);
    }
}
