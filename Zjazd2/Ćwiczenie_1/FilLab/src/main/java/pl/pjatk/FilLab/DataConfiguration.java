package pl.pjatk.FilLab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    // Inne beany i konfiguracje...

    // ...
}

