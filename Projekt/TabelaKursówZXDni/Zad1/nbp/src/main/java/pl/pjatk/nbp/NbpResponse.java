package pl.pjatk.nbp;

import java.util.List;

public class NbpResponse {
    private List<Table> rates;

    public List<Table> getRates() {
        return rates;
    }

    public void setRates(List<Table> rates) {
        this.rates = rates;
    }
}