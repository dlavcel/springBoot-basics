package courseworkspring.errorHandling;

public class PublicationRecordNotFound extends RuntimeException {
    public PublicationRecordNotFound(int id) {
        super("Publication record with id " + id + " not found");
    }
}
