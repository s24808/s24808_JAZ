package pl.pjatk.RentalService.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;

@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie getMovie(Long id) {
        // Wywołanie REST do MovieService, pobranie filmu po ID
        String url = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(url, Movie.class);
    }

    public boolean returnMovie(Long id) {
        // Wywołanie REST do MovieService, zmiana wartości pola is_available na true
        String url = "http://localhost:8080/movies/return/" + id;
        restTemplate.put(url, null);
        return true;
    }
}
