package courseworkspring.repos;

import courseworkspring.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client getClientByLoginAndPassword(String login, String password);
}