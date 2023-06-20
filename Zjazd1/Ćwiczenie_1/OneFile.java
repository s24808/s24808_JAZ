package pl.pjatk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component // Oznaczenie klasy jako komponentu zarządzanego przez Springa
public class FirstComponent {
    private SecondComponent secondComponent;

    public FirstComponent(SecondComponent secondComponent) {
        this.secondComponent = secondComponent;
    }

    public void printComponentName() {
        System.out.println("First Component");
    }
}

@Component // Oznaczenie klasy jako komponentu zarządzanego przez Springa
public class SecondComponent {
    public void printComponentName() {
        System.out.println("Second Component");
    }
}

@SpringBootApplication
@ComponentScan("pl.pjatk") // Skanowanie pakietu pl.pjatk w poszukiwaniu komponentów

public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

        FirstComponent firstComponent = context.getBean(FirstComponent.class);
        SecondComponent secondComponent = context.getBean(SecondComponent.class);

        firstComponent.printComponentName();
        secondComponent.printComponentName();
    }
}
