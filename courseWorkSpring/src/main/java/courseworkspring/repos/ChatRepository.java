package courseworkspring.repos;

import courseworkspring.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByPublicationId(int publicationId);
}

