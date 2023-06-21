package pl.pjatk.FilLab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @GetMapping("/info/{param}")
    public ResponseEntity<String> getInfoFromPathVariable(@PathVariable String param) {
        return ResponseEntity.ok("Info from PathVariable: " + param);
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfoFromRequestParam(@RequestParam("param") String param) {
        return ResponseEntity.ok("Info from RequestParam: " + param);
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<String> putData(@PathVariable String id, @RequestBody String data) {
        // Logika przetwarzania danych dostarczonych w ciele żądania PUT
        // Tutaj możesz wykonać logikę aktualizacji danych na podstawie dostarczonych danych
        return ResponseEntity.ok("Data updated for ID: " + id + ", Data: " + data);
    }

    @PostMapping("/data")
    public ResponseEntity<String> postData(@RequestBody String data) {
        // Logika przetwarzania danych dostarczonych w ciele żądania POST
        // Tutaj możesz wykonać logikę tworzenia nowych danych na podstawie dostarczonych danych
        return ResponseEntity.ok("Data created: " + data);
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable String id) {
        // Logika usuwania danych na podstawie ID
        // Tutaj możesz wykonać logikę usuwania danych na podstawie dostarczonego ID
        return ResponseEntity.ok().build();
    }
}