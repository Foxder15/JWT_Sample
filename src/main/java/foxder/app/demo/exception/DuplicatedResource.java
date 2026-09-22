package foxder.app.demo.exception;

public class DuplicatedResource extends RuntimeException {
    public DuplicatedResource(String message) {
        super(message);
    }
}
