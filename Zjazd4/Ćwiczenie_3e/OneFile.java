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

import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (movie.getId() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Movie newMovie = movieService.addMovie(movie);
        if (newMovie != null) {
            return ResponseEntity.ok(newMovie);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            updatedMovie.setId(id);
            movieService.updateMovie(updatedMovie);
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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

    private List<Movie> movies;
    private int nextId;
    public MovieService() {
        movies = new ArrayList<>();
        movies.add(new Movie(1, "Film 1", "Kategoria 1"));
        movies.add(new Movie(2, "Film 2", "Kategoria 2"));
        movies.add(new Movie(3, "Film 3", "Kategoria 3"));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie getMovieById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == (id)) {
                return movie;
            }
        }
        return null;
    }

    public Movie addMovie(Movie movie) {
        movie.setId(nextId++);
        movies.add(movie);
        return movie;
    }

    public void updateMovie(Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getId() == updatedMovie.getId()) {
                movies.set(i, updatedMovie);
                break;
            }
        }
    }

    // Pozostałe metody serwisowe
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
