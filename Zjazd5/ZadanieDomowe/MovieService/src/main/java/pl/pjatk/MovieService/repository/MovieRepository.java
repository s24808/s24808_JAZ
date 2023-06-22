package pl.pjatk.MovieService.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pjatk.MovieService.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Modifying
    @Query("UPDATE Movie m SET m.isAvailable = true WHERE m.id = :id")
    void updateAvailability(@Param("id") int id);

    // Pozostałe metody związane z dostępem do danych
}
