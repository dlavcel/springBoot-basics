package courseworkspring;

import courseworkspring.model.Client;
import courseworkspring.repos.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;


@SpringBootApplication
public class CourseWorkSpringApplication {

    private static final Logger logger = LoggerFactory.getLogger(CourseWorkSpringApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CourseWorkSpringApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ClientRepository repository) {
        return (args) -> {
            logger.info("All clients found with findAll():");
            logger.info("-------------------------------");
            for (Client client : repository.findAll()) {
                logger.info(client.toString());
            }
            logger.info("");

            // fetch person by id
            Optional<Client> person = repository.findById(1);
            logger.info("User found with findById(1):");
            logger.info("--------------------------------");
            person.ifPresent(value -> logger.info(value.toString()));
            logger.info("");
        };
    }
}
