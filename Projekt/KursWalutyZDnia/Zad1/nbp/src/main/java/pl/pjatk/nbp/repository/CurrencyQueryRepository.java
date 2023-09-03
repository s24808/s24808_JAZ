package pl.pjatk.nbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.nbp.model.CurrencyQuery;

@Repository
public interface CurrencyQueryRepository extends JpaRepository<CurrencyQuery, Long> {
}