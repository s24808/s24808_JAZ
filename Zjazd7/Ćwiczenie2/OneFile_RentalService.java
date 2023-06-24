// Klasa Movie

package pl.pjatk.RentalService.model;

public class Movie {
    private int id;
    private String name;
    private String category;
    private boolean isAvailable = false;

    public Movie() {

    }

    public Movie(int id, String name, String category, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Gettery i settery


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

//Klasa RentalController

package pl.pjatk.RentalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;

import pl.pjatk.RentalService.model.Movie;
import pl.pjatk.RentalService.service.RentalService;

@RestController
@RequestMapping("/rental")
@Tag(name = "Rental Controller", description = "Endpoints for movie rentals")
public class RentalController {

    private final RestTemplate restTemplate;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RestTemplate restTemplate, RentalService rentalService) {
        this.restTemplate = restTemplate;
        this.rentalService = rentalService;
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Get movie by ID", description = "Get movie details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public Movie getMovie(@PathVariable Long id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(movieServiceUrl, Movie.class);
    }

    @PutMapping("/movies/{id}/rent")
    @Operation(summary = "Rent a movie", description = "Rent a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie rented"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "502", description = "Movie service error"),
            @ApiResponse(responseCode = "504", description = "Gateway timeout")
    })
    public Movie rentMovie(@PathVariable int id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id + "/availability";

        try {
            return restTemplate.exchange(movieServiceUrl, HttpMethod.PUT, null, Movie.class).getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT);
        }
    }
}

//Klasa RentalService - to może nie istnieć w sumie

package pl.pjatk.RentalService.service;

import org.springframework.stereotype.Service;
import pl.pjatk.RentalService.model.Movie;

@Service
public class RentalService {

    public void updateAvailability(Long id) {
        // Logika aktualizacji dostępności filmu w systemie RentalService
        // Możesz zaimplementować tutaj odpowiednie operacje na danych lub wywołać inny serwis
        // w celu zaktualizowania dostępności filmu.
    }
}

//Main Application

package pl.pjatk.RentalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

//application.properties

server.port=8081

//dependencies
  
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
