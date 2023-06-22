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
