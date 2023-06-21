package pl.pjatk.FilLab;

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



