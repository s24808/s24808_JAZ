package pl.pjatk.MovieService.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import pl.pjatk.MovieService.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByNameContainingIgnoreCase(String name);
    List<Movie> findAllByCategory(String category);

    @Query("SELECT m FROM Movie m WHERE m.name LIKE %:keyword% OR m.category LIKE %:keyword%")
    List<Movie> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM Movie m WHERE m.isAvailable = true")
    List<Movie> findAllAvailableMovies();
}
