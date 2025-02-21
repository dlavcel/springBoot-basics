package courseworkspring.repos;

import courseworkspring.model.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalRepository extends JpaRepository<Periodical, Integer> {
}
