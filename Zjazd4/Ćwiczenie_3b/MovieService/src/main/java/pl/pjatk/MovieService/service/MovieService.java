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
