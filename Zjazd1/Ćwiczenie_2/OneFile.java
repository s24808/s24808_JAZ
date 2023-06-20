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

    public void printInfo() {
        System.out.println("First Component - Metoda: printInfo");
    }
}

@Component // Oznaczenie klasy jako komponentu zarządzanego przez Springa
public class SecondComponent {
    public void displayInfo() {
        System.out.println("Second Component - Metoda: displayInfo");
    }
}

@Component // Oznaczenie klasy jako komponentu zarządzanego przez Springa
public class ComponentResolver {
    private ApplicationContext applicationContext;

    public ComponentResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(String name, Class<T> componentClass) {
        return applicationContext.getBean(name, componentClass);
    }
}

@SpringBootApplication
@ComponentScan("pl.pjatk") // Skanowanie pakietu pl.pjatk w poszukiwaniu komponentów

public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

        ComponentResolver componentResolver = context.getBean(ComponentResolver.class);
        FirstComponent firstComponent = componentResolver.getBean("firstComponent", FirstComponent.class);
        SecondComponent secondComponent = componentResolver.getBean("secondComponent", SecondComponent.class);

        firstComponent.printInfo();
        secondComponent.displayInfo();
    }
}
