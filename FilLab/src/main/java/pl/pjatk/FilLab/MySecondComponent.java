package pl.pjatk.FilLab;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MySecondComponent {
    public MySecondComponent(MyFirstComponent myFirstComponent){
        System.out.println("Hello from MySecondComponent");
        myFirstComponent.printFirstComponentMessage();
    }

    public void printSecondComponentMessage(){
        System.out.println("Hello from my SecondComponent from method printSecondComponentMessage");
    }
}

