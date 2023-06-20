package pl.pjatk.FilLab;

import org.springframework.stereotype.Component;

@Component
public class SecondComponent {
    public String getName() {
        return "Second Component";
    }

    public void displayInfo() {
        System.out.println("Second Component - Metoda: displayInfo");
    }
}
