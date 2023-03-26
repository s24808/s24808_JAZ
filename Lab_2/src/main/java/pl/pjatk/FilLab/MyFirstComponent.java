package pl.pjatk.FilLab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyFirstComponent {
    public MyFirstComponent(@Value("${my.custom.property:Default Value}") String valueFromProperties){
        System.out.println(valueFromProperties);
    }
}