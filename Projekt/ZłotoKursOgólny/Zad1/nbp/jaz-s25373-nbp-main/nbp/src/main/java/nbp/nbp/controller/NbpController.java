package nbp.nbp.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nbp.nbp.service.NbpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/nbp")
public class NbpController {
private final NbpService nbpService;

    public NbpController(NbpService nbpService) {
        this.nbpService = nbpService;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "404 Not Found - UPSI DAISY"),
            @ApiResponse(responseCode = "400", description = "400 Bad Request - kurza twarz")
    })
            @GetMapping(value = "/{startDate}/{endDate}/{waluta}")
            @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "upsi daisy - wiem, że i tak pokarze się BAD REQUEST ale no można dodać reason zdarzenia")
 @Operation(description = "Program oblicza avg kursu walut z podanego przedziału czasu oraz w danej walucie :)" )
    public ResponseEntity<Double> getnpb(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String waluta){

        return ResponseEntity.ok(nbpService.show(startDate,endDate,waluta));
    }
}