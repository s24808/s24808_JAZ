package pl.pjatk.FilLab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Controller {
//    @GetMapping("/hello")
//    public ResponseEntity<String> helloWorld(){
//        return ResponseEntity.ok("Hello World!");
//    }
//    @GetMapping("/model")
//    public ResponseEntity<Car> getCar(){
//        return ResponseEntity.ok(new Car("Coupe", "Hyundai"));
//    }

    @GetMapping("/hello/{someValue}")
    public ResponseEntity<String> getText(@PathVariable String someValue){
        return ResponseEntity.ok(someValue);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getParam(@RequestParam String reqParam){
        return ResponseEntity.ok(reqParam);
    }

    @PostMapping("/model")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car newCar = new Car(car.getModel(), car.getMake());
        return ResponseEntity.ok(newCar);
    }
}

