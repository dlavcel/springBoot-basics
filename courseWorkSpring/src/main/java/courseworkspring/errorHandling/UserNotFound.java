package courseworkspring.errorHandling;

public class UserNotFound extends RuntimeException {
    public UserNotFound(int id) {
        super("Could not find user " + id);
    }
}