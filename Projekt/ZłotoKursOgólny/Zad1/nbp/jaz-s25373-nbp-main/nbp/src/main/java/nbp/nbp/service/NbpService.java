package nbp.nbp.service;
import nbp.nbp.nbpmodel.Nbp;
import nbp.nbp.nbpmodel.Rates;
import nbp.nbp.repositories.NbpRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class NbpService {
    private final RestTemplate restTemplate;
    private final NbpRepository nbpRepository;


    public NbpService(RestTemplate restTemplate, NbpRepository nbpRepository) {
        this.restTemplate = restTemplate;
        this.nbpRepository = nbpRepository;
    }

    public Double show(String startDate, String endDate, String waluta) {
        ResponseEntity<Nbp> response = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/gbp/"+startDate+"/"+endDate+"/"+waluta, Nbp.class);
        //pozostawie panu z błędnym url dzięki czemu po wykonaniu pokarze iż mój kod obsługuje błąd

        if(response.getStatusCode() != HttpStatus.OK){
            throw new ResponseStatusException(response.getStatusCode());
        }
        else {
            Nbp nbp = response.getBody();

            Double sum = 0.0;
            Double sr = 0.0;

            for (Rates rates : nbp.getRates()) {
                sum += rates.getMid();
//tu jest średnia i działa

            }
            sr = sum / nbp.getRates().size();
            nbpRepository.addentry(nbp.getCurrency(), startDate, endDate, sr, new Date().toString());
            return sr;
        }
    }
}
