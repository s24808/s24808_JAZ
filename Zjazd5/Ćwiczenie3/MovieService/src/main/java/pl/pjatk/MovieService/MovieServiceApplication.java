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



