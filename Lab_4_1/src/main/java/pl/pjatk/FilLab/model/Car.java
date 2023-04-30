package pl.pjatk.FilLab.model;

public class Car {
    private String model, make;

    public Car(String model, String make) {
        this.model = model;
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", make='" + make + '\'' +
                '}';
    }
}
