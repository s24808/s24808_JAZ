package pl.pjatk.FilLab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.FilLab.model.Car;
import pl.pjatk.FilLab.service.SandboxService;

@RestController
@RequestMapping("/test")
public class Controller {



    @PostMapping("/model")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car newCar = new Car(car.getModel(), car.getMake());
        return ResponseEntity.ok(newCar);
    }

    private final SandboxService sandboxService;

    public Controller(SandboxService sandboxService) {
        this.sandboxService = sandboxService;
    }

    @GetMapping("/hello/ex")
    public ResponseEntity<String> exception() {
        String responseText = sandboxService.throwException(true);
        return ResponseEntity.ok(responseText);
    }
}

