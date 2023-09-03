//Klasa Model

package pl.pjatk.nbp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "currency_query")
@Schema(description = "Waluty Tabela")
public class CurrencyQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    @Column(name = "id")
    private Long id;

    @Schema(description = "Waluta")
    @Column(name = "currency")
    private String currency;

    @Schema(description = "Ilość_dni")
    @Column(name = "numberofdays")
    private int numberOfDays;

    @Schema(description = "Aktualny Kurs")
    @Column(name = "exchangerate")
    private double exchangeRate;

    @Schema(description = "Czas")
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "Średnia")
    @Column(name = "calculated_rate")
    private double calculatedRate;

    @Schema(description = "Data")
    @Column(name = "query_date")
    private LocalDate queryDate;

    @Schema(description = "Godzina")
    @Column(name = "query_time")
    private LocalTime queryTime;

    // konstruktory, gettery, settery

    public CurrencyQuery() {
    }

    public void setQueryDate(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    public void setQueryTime(LocalTime queryTime) {
        this.queryTime = queryTime;
    }

    public void setCalculatedRate(double calculatedRate) {
        this.calculatedRate = calculatedRate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

// Klasa CurrencyQueryRepository

package pl.pjatk.nbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.nbp.model.CurrencyQuery;

@Repository
public interface CurrencyQueryRepository extends JpaRepository<CurrencyQuery, Long> {
    default CurrencyQuery saveCurrencyQuery(CurrencyQuery currencyQuery) {
        return save(currencyQuery);
    }
}

//Klasa CurrencyService

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

// Klasa CurrencyController

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

// Klasa RestExceptionHandler (RESTCONTROLLERADVICE)

package pl.pjatk.nbp.excepetion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleNbpApiException(Exception ex) {
        // Obsługa błędu zwracanego przez API NBP
        String errorMessage = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Domyślny kod odpowiedzi HTTP

        if (ex instanceof HttpClientErrorException) {
            httpStatus = (HttpStatus) ((HttpClientErrorException) ex).getStatusCode();
        } else if (ex instanceof HttpServerErrorException) {
            httpStatus = (HttpStatus) ((HttpServerErrorException) ex).getStatusCode();
        }

        // Tworzenie odpowiedzi HTTP z odpowiednim kodem błędu i treścią błędu
        ApiResponse errorResponse = new ApiResponse(httpStatus, errorMessage);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}

// Klasa ApiResponse (DO OBLUSLUGI BLEDOW Z RestExceptionHandler)

package pl.pjatk.nbp.excepetion;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private HttpStatus status;
    private String message;

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

//Klasa NbpResponse

package pl.pjatk.nbp;

import java.util.List;

public class NbpResponse {
    private List<Rate> rates;

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}

//Klasa Rate

package pl.pjatk.nbp;

public class Rate {
    private double mid;

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }
}


//Main Application

package pl.pjatk.nbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NbpApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(NbpApplication.class, args);
	}

}

//application.properties

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/nbp
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=validate

//Dependencies

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- Spring Web -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.33</version>
			<scope>runtime</scope>
		</dependency>

		<!-- Spring Data JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- Swagger -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.1.0</version>
		</dependency>
