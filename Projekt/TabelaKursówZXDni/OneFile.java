//Klasa Model

package pl.pjatk.nbp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "currency_query5")
@Schema(description = "Waluty Tabela")
public class CurrencyQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    @Column(name = "id")
    private Long id;

    @Schema(description = "Waluta")
    @Column(name = "currency")
    private CurrencyType currency;

    @Schema(description = "Kurs")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public LocalDate getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    public LocalTime getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(LocalTime queryTime) {
        this.queryTime = queryTime;
    }

    public double getCalculatedRate() {
        return calculatedRate;
    }

    public void setCalculatedRate(double calculatedRate) {
        this.calculatedRate = calculatedRate;
    }
}

//Klasa CurrencyType

package pl.pjatk.nbp.model;

public enum CurrencyType {
    USD, EUR, GBP, PLN
}

//Klasa Controller

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

    @GetMapping("/currency/{currency}")
    @Operation(summary = "Pobierz kurs waluty", description = "Pobierz kurs danej waluty z ostatniej tabeli kursów")
    public void getCurrencyExchangeRate(@PathVariable String currency) {
        currencyService.saveCurrencyQuery(currency);
    }
}

//Klasa Config

package pl.pjatk.nbp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

//Klasa Service

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

//Obsługa błędów
//Klasa RestExceptionHandler

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

//Klasa ApiResponse

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

//Klasa Repository

package pl.pjatk.nbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.nbp.model.CurrencyQuery;

@Repository
public interface CurrencyQueryRepository extends JpaRepository<CurrencyQuery, Long> {
}

//Klasa NbpResponse

package pl.pjatk.nbp;

import java.util.List;

public class NbpResponse {
    private List<Table> rates;

    public List<Table> getRates() {
        return rates;
    }

    public void setRates(List<Table> rates) {
        this.rates = rates;
    }
}

//Klasa Rate

package pl.pjatk.nbp;
public class Rate {
    private String currency;
    private double mid;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }
}

//Klasa Table

package pl.pjatk.nbp;

import java.util.List;

public class Table {
    private List<Rate> rates;

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
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
