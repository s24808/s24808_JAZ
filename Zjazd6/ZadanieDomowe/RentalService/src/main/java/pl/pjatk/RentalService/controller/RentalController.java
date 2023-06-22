package pl.pjatk.RentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/movies/{id}/rent")
    public Movie rentMovie(@PathVariable int id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id + "/availability";

        try {
            return restTemplate.exchange(movieServiceUrl, HttpMethod.PUT, null, Movie.class).getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT);
        }
    }
}