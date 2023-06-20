package pl.pjatk.FilLab;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

        @Bean
        @ConditionalOnProperty(name = "custom.flag", havingValue = "true")
        public Pojo pojo() {
                return new Pojo();
        }

        // Inne beany i konfiguracje...

        // ...
}


