package pl.pjatk.FilLab;

//Klasa POJO

public class Pojo {
    private int id;
    private String name;
    private boolean active;

    // Konstruktory, gettery, settery, toString

    // Konstruktor bezargumentowy
    public Pojo() {
    }

    // Konstruktor z argumentami
    public Pojo(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    // Gettery i Settery dla pól

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Metoda toString()
    @Override
    public String toString() {
        return "Pojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}


//Klasa DataConfiguration
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
