package pl.pjatk.MovieService.service;

import org.springframework.stereotype.Service;
import pl.pjatk.MovieService.model.Movie;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private List<Movie> movies;

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

    // Pozostałe metody serwisowe
}
