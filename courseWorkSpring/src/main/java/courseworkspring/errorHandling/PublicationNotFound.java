package courseworkspring.errorHandling;

public class PublicationNotFound extends RuntimeException {
    public PublicationNotFound(int id) {
        super("Publication not found with id " + id);
    }
}
