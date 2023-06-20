package pl.pjatk.FilLab;

import org.springframework.stereotype.Component;

@Component
public class FirstComponent {
    private SecondComponent secondComponent;

    public FirstComponent(SecondComponent secondComponent) {
        this.secondComponent = secondComponent;
    }

    public String getName() {
        return "First Component";
    }

    public void printInfo() {
        System.out.println("First Component - Metoda: printInfo");
    }
}
