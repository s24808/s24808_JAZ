//Klasa Movie

package pl.pjatk.MovieService.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean isAvailable;

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

//Klasa MovieRepository

package pl.pjatk.MovieService.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import pl.pjatk.MovieService.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByNameContainingIgnoreCase(String name);
    List<Movie> findAllByCategory(String category);

    @Query("SELECT m FROM Movie m WHERE m.name LIKE %:keyword% OR m.category LIKE %:keyword%")
    List<Movie> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM Movie m WHERE m.isAvailable = true")
    List<Movie> findAllAvailableMovies();
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

    public boolean deleteMovie(int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
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

    @PutMapping("/{id}/available")
    public ResponseEntity<Movie> markMovieAsAvailable(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            movie.setAvailable(true);
            movieService.updateMovie(movie);
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
