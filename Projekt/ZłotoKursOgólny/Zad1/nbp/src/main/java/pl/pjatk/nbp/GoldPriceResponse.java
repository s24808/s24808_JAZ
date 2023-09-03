package pl.pjatk.nbp;


import java.util.List;

public class GoldPriceResponse {
    private List<GoldPriceData> data;

    public List<GoldPriceData> getData() {
        return data;
    }

    public void setData(List<GoldPriceData> data) {
        this.data = data;
    }
}