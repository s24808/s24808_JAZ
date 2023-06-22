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

//Klasa MovieController

package pl.pjatk.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;
import pl.pjatk.RentalService.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        // Wywołanie REST do MovieService, pobranie filmu po ID
        Movie movie = movieService.getMovie(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<String> returnMovie(@PathVariable Long id) {
        // Wywołanie REST do MovieService, zmiana wartości pola is_available na true
        boolean success = movieService.returnMovie(id);
        if (success) {
            return ResponseEntity.ok("Movie with ID " + id + " returned successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


//Klasa MovieService

package pl.pjatk.RentalService.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;

@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie getMovie(Long id) {
        // Wywołanie REST do MovieService, pobranie filmu po ID
        String url = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(url, Movie.class);
    }

    public boolean returnMovie(Long id) {
        // Wywołanie REST do MovieService, zmiana wartości pola is_available na true
        String url = "http://localhost:8080/movies/return/" + id;
        restTemplate.put(url, null);
        return true;
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
