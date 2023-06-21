package pl.pjatk.MovieService.model;

public class Movie {
    private int id;
    private String name;
    private String category;
    // Dodaj inne pola według uznania

    public Movie(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Konstruktory, gettery i settery

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Dodaj gettery i settery dla innych pól
}