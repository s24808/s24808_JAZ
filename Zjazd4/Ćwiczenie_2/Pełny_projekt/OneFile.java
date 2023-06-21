// Klasa Car

public class Car {
    private String brand;
    private String model;
    private int year;

    // Konstruktory, gettery, settery

    // Przykładowe implementacje konstruktora i getterów/setterów
    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

//Klasa HelloController

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    /*@GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping("/model")
    public ResponseEntity<Car> getModel() {
        Car car = new Car("Brand", "Model", 2023);
        return ResponseEntity.ok(car);
    }*/

    @GetMapping("/hello/{someValue}")
    public ResponseEntity<String> helloValue(@PathVariable String someValue) {
        return ResponseEntity.ok(someValue);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloRequestParam(@RequestParam("reqParam") String someValue) {
        return ResponseEntity.ok(someValue);
    }

    @PostMapping("/model")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        // Przetwarzanie danych dostarczonych w body
        // Tutaj możesz wykonać logikę tworzenia nowego obiektu na podstawie dostarczonych danych

        // Przykładowa implementacja
        Car newCar = new Car(car.getBrand(), car.getModel(), car.getYear());

        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/exception")
    public ResponseEntity<String> throwException(@RequestParam("errorMessage") String errorMessage) {
        return ResponseEntity.badRequest().body(helloService.handleException(errorMessage));
    }
}

//Klasa HelloService - po refactorze i dodaniu adnotacji @Service

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String handleException(String errorMessage) {
        throw new CustomException(errorMessage);
    }
}

//Klasa Custom Exception - wywołanie customowej nazwy z klasy CustomExcepetionHandler - Przeniesione do paczki z HelloController

class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}

//Klasa CustomExcepetionHandler - customowa nazwa - Przeniesione do paczki z HelloController 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        String errorMessage = "Exception occurred on request. Exception message: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

//Main Application

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilLabApplication.class, args);
	}
}
