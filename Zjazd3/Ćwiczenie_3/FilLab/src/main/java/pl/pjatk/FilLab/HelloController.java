package pl.pjatk.FilLab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping("/model")
    public ResponseEntity<Car> getModel() {
        Car car = new Car("Brand", "Model", 2023);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/hello/{someValue}")
    public ResponseEntity<String> helloValue(@PathVariable String someValue) {
        return ResponseEntity.ok(someValue);
    }
}