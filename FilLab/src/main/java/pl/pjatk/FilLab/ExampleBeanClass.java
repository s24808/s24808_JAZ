package pl.pjatk.FilLab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleBeanClass {
    @Bean
    public Car exampleBean(){
        return new Car();
    }
}
