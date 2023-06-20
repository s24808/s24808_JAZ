//Klasa POJO
public class Pojo {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Pojo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

//Klasa DataConfiguration po dodaniu wartości z application.properties
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@Configuration
public class DataConfiguration {

        // Bean z wcześniej utworzonej klasy MyClass
        @Bean
        public Pojo exampleBean(){
            return new Pojo( 1 , "hhhh"); //Tutaj może być puste!
        }

        //Lista 5 stringów
        @Bean
        public List<String> defaultData(){
            return List.of("string1", "string2", "string3", "string4", "string5");
        }

        //Wypisywanie wartości z application.properties podczas tworzenia beana
        @Value("${my.custom.property:Default value}")
        private String customProperty;

        //Wyświetlanie wartości z application.properties jeśli jest podany
        @Bean
        public void printCustomProperty() {
                System.out.println("Custom property value: " + customProperty);
        }

        //Wyświetlanie domyślnej wartości z application.properties
        @Bean
        public void printDefaultValue() {
                System.out.println("Default value: " + "${my.custom.property:Default value}");
        }

        // Inne beany i konfiguracje...
}

//Klasa FilLabApplication
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilLabApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilLabApplication.class, args);

	}
}

//apllication.properties
my.custom.property=Hello from application properties  //To ma być puste!!!!

//application.yml
my:
  custom:
    property: Hello from application properties //Jeśli to będzie puste to wartość będzie wyświetlana jako Default Value
