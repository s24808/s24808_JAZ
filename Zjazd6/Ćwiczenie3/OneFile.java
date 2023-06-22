// Klasa Movie

package pl.pjatk.RentalService.model;

public class Movie {
    private int id;
    private String name;
    private String category;

    public Movie() {
    }

    public Movie(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

//Klasa RentalController

package pl.pjatk.RentalService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RestTemplate restTemplate;

    public RentalController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Dodatkowe metody kontrolera
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) {
        String url = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(url, Movie.class);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<String> returnMovie(@PathVariable Long id) {
        String url = "http://localhost:8080/movies/" + id + "/return";
        restTemplate.postForObject(url, null, Void.class);
        return ResponseEntity.ok("Movie returned successfully");
    }

}

//Klasa RentalService

package pl.pjatk.RentalService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;

@Service
public class RentalService {

    private final RestTemplate restTemplate;

    public RentalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Dodatkowe metody serwisu
    public Movie getMovie(Long id) {
        String url = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(url, Movie.class);
    }

    public void returnMovie(Long id) {
        String url = "http://localhost:8080/movies/" + id + "/return";
        restTemplate.postForObject(url, null, Void.class);
    }
}

//Main Application

package pl.pjatk.RentalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
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
