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
