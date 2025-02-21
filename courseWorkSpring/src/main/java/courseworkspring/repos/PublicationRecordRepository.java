package courseworkspring.repos;

import courseworkspring.model.PublicationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRecordRepository extends JpaRepository<PublicationRecord, Integer> {
}
