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
}
