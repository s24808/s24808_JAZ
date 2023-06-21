//Klasa Movie

package pl.pjatk.MovieService.model;

public class Movie {
    private int id;
    private String name;
    private String category;
    // Dodaj inne pola według uznania

    public Movie(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Konstruktory, gettery i settery

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

    // Dodaj gettery i settery dla innych pól
}

//Klasa MovieController

package pl.pjatk.MovieService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MovieService.model.Movie;
import pl.pjatk.MovieService.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
}

//Klasa MovieService

package pl.pjatk.MovieService.service;

import org.springframework.stereotype.Service;
import pl.pjatk.MovieService.model.Movie;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    public List<Movie> getAllMovies() {
        // Tutaj możesz zaimplementować logikę pobierania wszystkich filmów
        // W tym przykładzie zwracam statyczną listę filmów

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Film 1", "Kategoria 1"));
        movies.add(new Movie(2, "Film 2", "Kategoria 2"));
        movies.add(new Movie(3, "Film 3", "Kategoria 3"));

        return movies;
    }
}

//Main Application

package pl.pjatk.MovieService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

}
