package pl.pjatk.RentalService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatusCode;

@Schema(description = "Movie entity")
public class Movie {
    @Schema(description = "Movie ID")
    private int id;
    @Schema(description = "Movie title")
    private String name;
    @Schema(description = "Movie category")
    private String category;
    @Schema(description = "Movie availability")
    private boolean isAvailable = false;

    public Movie() {

    }

    public Movie(int id, String name, String category, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Gettery i settery


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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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