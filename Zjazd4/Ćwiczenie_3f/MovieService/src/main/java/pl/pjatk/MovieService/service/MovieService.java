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

    public boolean deleteMovie(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                movies.remove(movie);
                return true;
            }
        }
        return false;
    }

    // Pozostałe metody serwisowe
}
