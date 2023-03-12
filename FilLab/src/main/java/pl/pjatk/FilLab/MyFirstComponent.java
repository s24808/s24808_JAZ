package pl.pjatk.FilLab;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyFirstComponent {
    public MyFirstComponent(){
        System.out.println("Hello from MyFirstComponent");
    }
    public void printFirstComponentMessage(){
        System.out.println("Hello from my FirstComponent from method printFirstComponentMessage");
    }
}
