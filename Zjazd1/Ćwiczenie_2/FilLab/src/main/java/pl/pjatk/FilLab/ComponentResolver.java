package pl.pjatk.FilLab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ComponentResolver {
    private ApplicationContext applicationContext;

    @Autowired
    public ComponentResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //Bez tego nie da się użyć getBean na firstcomponent i secondcomponent w FilLabApplication
    public <T> T getBean(String name, Class<T> componentClass) {
        return applicationContext.getBean(name, componentClass);
    }
}
