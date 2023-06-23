package pl.pjatk.RentalService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;

import pl.pjatk.RentalService.model.Movie;
import pl.pjatk.RentalService.service.RentalService;

@RestController
@RequestMapping("/rental")
@Tag(name = "Rental Controller", description = "Endpoints for movie rentals")
public class RentalController {

    private final RestTemplate restTemplate;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RestTemplate restTemplate, RentalService rentalService) {
        this.restTemplate = restTemplate;
        this.rentalService = rentalService;
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Get movie by ID", description = "Get movie details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public Movie getMovie(@PathVariable Long id) {
        String movieServiceUrl = "http://localhost:8080/movies/" + id;
        return restTemplate.getForObject(movieServiceUrl, Movie.class);
    }

    @PutMapping("/movies/{id}/rent")
    @Operation(summary = "Rent a movie", description = "Rent a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie rented"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "502", description = "Movie service error"),
            @ApiResponse(responseCode = "504", description = "Gateway timeout")
    })
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