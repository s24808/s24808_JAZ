//Klasa Movie

package pl.pjatk.MovieService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
@Schema(description = "Movie entity")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Movie ID")
    private int id;
    @Schema(description = "Movie title")
    @Column(name = "name")
    private String name;
    @Schema(description = "Movie category")
    @Column(name = "category")
    private String category;

    @Column(name = "is_available")
    @Schema(description = "Movie availability")
    private boolean isAvailable = false;

    public Movie() {
        
    }

    // Getters and Setters


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

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
                ", isAvailable=" + isAvailable +
                '}';
    }
}

//Klasa MovieRepository

package pl.pjatk.MovieService.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pjatk.MovieService.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Modifying
    @Query("UPDATE Movie m SET m.isAvailable = true WHERE m.id = :id")
    void updateAvailability(@Param("id") int id);

    // Pozostałe metody związane z dostępem do danych
}

//Klasa MovieService

package pl.pjatk.MovieService.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void updateAvailability(int id) {
        movieRepository.updateAvailability(id);
    }
}

//Klasa MovieController

package pl.pjatk.MovieService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.MovieService.model.Movie;
import pl.pjatk.MovieService.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movie Controller", description = "Endpointy do zarządzania filmami")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieve all movies from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found"),
            @ApiResponse(responseCode = "204", description = "No movies found")
    })
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID", description = "Retrieve movie details by its ID")
    @ApiResponse(responseCode = "200", description = "Movie found")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Add new movie", description = "Add a new movie to the database")
    @ApiResponse(responseCode = "201", description = "Movie created")
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
    @Operation(summary = "Delete movie", description = "Delete a movie by its ID")
    @ApiResponse(responseCode = "204", description = "Movie deleted")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/* //To musi być zakomntarzowane ze względu na powielające się wartości - tutaj ustawia wartość na TRUE, niżej FALSE
    @PutMapping("/{id}/availability")
    @Operation(summary = "Update movie availability", description = "Update the availability of a movie by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movie availability updated"),
        @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Movie> updateAvailability(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            movieService.updateAvailability(id);
            movie.setAvailable(true); // Ustawienie wartości is_available na true
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
*/
    @PutMapping("/{id}/availability")
    @Operation(summary = "Update movie availability", description = "Update the availability of a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie availability updated"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Movie> updateAvailability(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            movieService.updateAvailability(id);
            movie.setAvailable(false); // Ustawienie wartości is_available na false
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
import org.springframework.context.ApplicationContext;
import pl.pjatk.MovieService.model.Movie;
import pl.pjatk.MovieService.service.MovieService;
import java.util.List;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MovieServiceApplication.class, args);
		MovieService movieService = context.getBean(MovieService.class);

		// Wywołanie metod w MovieService:

		// Ustawienie dostępności filmu o id 1 na true
		movieService.updateAvailability(1);

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
		updatedMovie.setId(2);
		updatedMovie.setName("Zaktualizowany film");
		updatedMovie.setCategory("Komedia");
		Movie updatedMovieResult = movieService.updateMovie(updatedMovie);
		System.out.println("Zaktualizowany film: " + updatedMovieResult);

		//Usuwanie rekordu z bazy
		movieService.deleteMovie(4);
		System.out.println("Film o ID 4 został usunięty");
	}
}
