package pl.pjatk.FilLab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FilLabApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FilLabApplication.class, args);

		ComponentResolver componentResolver = context.getBean(ComponentResolver.class);
		FirstComponent firstComponent = componentResolver.getBean("firstComponent", FirstComponent.class);
		SecondComponent secondComponent = componentResolver.getBean("secondComponent", SecondComponent.class);

		firstComponent.printInfo();
		secondComponent.displayInfo();
	}
}
