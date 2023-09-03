package nbp.nbp.repositories;
import jakarta.transaction.Transactional;
import nbp.nbp.nbpmodel.DatabaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
public interface NbpRepository  extends JpaRepository<DatabaseEntry, Integer>{
    @Query(value = "INSERT INTO java(walute, DATE_START, DATE_END, WYNIK, DATA_ZAPISU) VALUES ( ?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    @Transactional
    @Modifying
    void addentry(String walute, String Date,String DATE_END,double WYNIK,String DATA_ZAPISU);

}
