package pl.pjatk.nbp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.nbp.model.CurrencyType;
import pl.pjatk.nbp.service.CurrencyService;

import java.time.LocalDate;

@RestController
@RequestMapping("/gold-price")
@Tag(name = "Kontroler Ceny Złota", description = "Endpointy do zarządzania ceną złota")
public class GoldPriceController {

    private final CurrencyService currencyService;

    @Autowired
    public GoldPriceController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/current")
    @Operation(summary = "Aktualna cena złota", description = "Pobierz aktualną cenę złota i zapisz ją do bazy danych")
    public ResponseEntity<Double> getCurrentGoldPrice() {
        double currentGoldPrice = currencyService.fetchAndSaveGoldPrice();
        return ResponseEntity.ok(currentGoldPrice);
    }
}
