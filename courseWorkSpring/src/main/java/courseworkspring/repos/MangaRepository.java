package courseworkspring.repos;

import courseworkspring.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Integer> {
}
