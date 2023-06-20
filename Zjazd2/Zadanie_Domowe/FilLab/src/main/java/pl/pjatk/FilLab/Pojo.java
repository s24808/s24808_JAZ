package pl.pjatk.FilLab;

public class Pojo {
    private int id;
    private String name;

    public Pojo() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Pojo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}