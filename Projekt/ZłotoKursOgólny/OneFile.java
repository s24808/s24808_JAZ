//Klasa Model

package pl.pjatk.nbp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "currency_query4")
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
    GOLD
}

//Klasa Controller

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

//Klasa GoldPriceResponse

package pl.pjatk.nbp;


import java.util.List;

public class GoldPriceResponse {
    private List<GoldPriceData> data;

    public List<GoldPriceData> getData() {
        return data;
    }

    public void setData(List<GoldPriceData> data) {
        this.data = data;
    }
}

//Klasa GoldPriceData

package pl.pjatk.nbp;

public class GoldPriceData {
    private String data;
    private double cena;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
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
