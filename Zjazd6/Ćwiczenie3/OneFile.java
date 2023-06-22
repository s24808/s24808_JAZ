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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;
import pl.pjatk.RentalService.service.RentalService;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RestTemplate restTemplate;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RestTemplate restTemplate, RentalService rentalService) {
        this.restTemplate = restTemplate;
        this.rentalService = rentalService;
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable Long id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(movieServiceUrl, Movie.class);
    }

    @PutMapping("/movies/{id}/return")
    public Movie returnMovie(@PathVariable int id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id + "/availability";
        return restTemplate.exchange(movieServiceUrl, HttpMethod.PUT, null, Movie.class).getBody();
    }
}

//Klasa RentalService

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
