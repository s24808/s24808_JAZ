package pl.pjatk.FilLab;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InjectedComponent{
    public InjectedComponent(ApplicationContext applicationContext){
        var car = applicationContext.getBean("exampleBean", Car.class);
        System.out.println(car.getName());
        MyFirstComponent myFirstComponent = applicationContext.getBean("myFirstComponent", MyFirstComponent.class);
        MySecondComponent mySecondComponent = applicationContext.getBean("mySecondComponent", MySecondComponent.class);
    myFirstComponent.printFirstComponentMessage();
    mySecondComponent.printSecondComponentMessage();
    }


}
