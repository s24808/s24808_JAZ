package pl.pjatk.FilLab;

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
        @Value("${my.custom.property}")
        private String customProperty;

        @Bean
        public void printCustomProperty() {
            System.out.println(customProperty);
        }

        // Inne beany i konfiguracje...
}

