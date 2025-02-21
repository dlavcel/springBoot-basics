package courseworkspring.errorHandling;

public class ChatNotFound extends RuntimeException {
    public ChatNotFound(int id) {
        super("Could not find chat with id " + id);
    }
}
