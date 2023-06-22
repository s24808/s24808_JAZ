//Klasa Movie

package pl.pjatk.MovieService.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    public Movie() {
        
    }

    // Getters and Setters

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

//Klasa MovieRepository

package pl.pjatk.MovieService.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pl.pjatk.MovieService.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    // Dodatkowe metody związane z dostępem do danych
}

//Klasa MovieService

package pl.pjatk.MovieService.service;

import org.springframework.stereotype.Service;
import pl.pjatk.MovieService.model.Movie;
import pl.pjatk.MovieService.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public boolean deleteMovie(int id) {
        movieRepository.deleteById(id);
        return false;
    }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

//Main Application

package pl.pjatk.MovieService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.pjatk.MovieService.model.Movie;
import pl.pjatk.MovieService.service.MovieService;
import java.util.List;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MovieServiceApplication.class, args);
		MovieService movieService = context.getBean(MovieService.class);

		// Wywołanie metod w MovieService

		//Wyświetlenie wszystkich filmów z listy
		List<Movie> movies = movieService.getAllMovies();
		System.out.println("Wszystkie filmy: " + movies);

		//Wyświetlenie filmu o id 1
		Movie movie = movieService.getMovieById(1);
		System.out.println("Film o ID 1: " + movie);

		//Dodanie filmu jako rekordu w bazie
		Movie newMovie = new Movie();
		newMovie.setName("Nowy film");
		newMovie.setCategory("Akcja");
		Movie addedMovie = movieService.addMovie(newMovie);
		System.out.println("Dodany film: " + addedMovie);

		//Zmiana rekordu w bazie
		Movie updatedMovie = new Movie();
		updatedMovie.setId(1);
		updatedMovie.setName("Zaktualizowany film");
		updatedMovie.setCategory("Komedia");
		Movie updatedMovieResult = movieService.updateMovie(updatedMovie);
		System.out.println("Zaktualizowany film: " + updatedMovieResult);

		//Usuwanie rekordu z bazy
		movieService.deleteMovie(4);
		System.out.println("Film o ID 4 został usunięty");
	}
}
