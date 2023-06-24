package pl.pjatk.MovieService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
@Schema(description = "Movie entity")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Movie ID")
    private int id;
    @Schema(description = "Movie title")
    @Column(name = "name")
    private String name;
    @Schema(description = "Movie category")
    @Column(name = "category")
    private String category;

    @Column(name = "is_available")
    @Schema(description = "Movie availability")
    private boolean isAvailable = false;

    public Movie() {
        
    }

    // Getters and Setters


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

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


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}