package pl.pjatk.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.RentalService.model.Movie;
import pl.pjatk.RentalService.service.RentalService;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RestTemplate restTemplate;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RestTemplate restTemplate, RentalService rentalService) {
        this.restTemplate = restTemplate;
        this.rentalService = rentalService;
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable Long id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(movieServiceUrl, Movie.class);
    }

    @PutMapping("/movies/{id}/return")
    public Movie returnMovie(@PathVariable int id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id + "/availability";
        return restTemplate.exchange(movieServiceUrl, HttpMethod.PUT, null, Movie.class).getBody();
    }

    @PutMapping("/movies/{id}/rent")
    public Movie rentMovie(@PathVariable int id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id + "/availability";
        return restTemplate.exchange(movieServiceUrl, HttpMethod.PUT, null, Movie.class).getBody();
    }
}