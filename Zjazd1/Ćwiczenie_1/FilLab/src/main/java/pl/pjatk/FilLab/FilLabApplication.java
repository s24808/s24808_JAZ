package pl.pjatk.FilLab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FilLabApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FilLabApplication.class, args);
		FirstComponent firstComponent = context.getBean(FirstComponent.class);
		SecondComponent secondComponent = context.getBean(SecondComponent.class);

		System.out.println(firstComponent.getName());
		System.out.println(secondComponent.getName());
	}
}
