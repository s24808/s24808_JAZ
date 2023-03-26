package pl.pjatk.FilLab;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigClass {
    @Bean
    public Pojo exampleBean(){
        return new Pojo( 1 , "hhhh");
    }

    @Bean
    public List<String> defaultData(){
        return List.of("string1", "string2", "string3", "string4", "string5");
    }

    @Bean
    @ConditionalOnProperty(name = "my.custom.value", havingValue = "true")
    public Pojo propertiesBean(){
        System.out.println("dupa");
        return new Pojo(2, "tttt");
    }
}
